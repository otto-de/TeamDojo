package de.otto.teamdojo.web.rest;

import de.otto.teamdojo.TeamdojoApp;

import de.otto.teamdojo.domain.TeamSkill;
import de.otto.teamdojo.domain.Skill;
import de.otto.teamdojo.domain.Team;
import de.otto.teamdojo.repository.TeamSkillRepository;
import de.otto.teamdojo.service.TeamSkillService;
import de.otto.teamdojo.service.dto.TeamSkillDTO;
import de.otto.teamdojo.service.mapper.TeamSkillMapper;
import de.otto.teamdojo.web.rest.errors.ExceptionTranslator;
import de.otto.teamdojo.service.dto.TeamSkillCriteria;
import de.otto.teamdojo.service.TeamSkillQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ArrayList;

import static de.otto.teamdojo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TeamSkillResource REST controller.
 *
 * @see TeamSkillResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TeamdojoApp.class)
public class TeamSkillResourceIntTest {

    private static final Instant DEFAULT_COMPLETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COMPLETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VERIFIED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VERIFIED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IRRELEVANT = false;
    private static final Boolean UPDATED_IRRELEVANT = true;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Integer DEFAULT_VOTE = 1;
    private static final Integer UPDATED_VOTE = 2;

    private static final String DEFAULT_VOTERS = "AAAAAAAAAA";
    private static final String UPDATED_VOTERS = "BBBBBBBBBB";

    @Autowired
    private TeamSkillRepository teamSkillRepository;



    @Autowired
    private TeamSkillMapper teamSkillMapper;
    

    @Autowired
    private TeamSkillService teamSkillService;

    @Autowired
    private TeamSkillQueryService teamSkillQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTeamSkillMockMvc;

    private TeamSkill teamSkill;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeamSkillResource teamSkillResource = new TeamSkillResource(teamSkillService, teamSkillQueryService);
        this.restTeamSkillMockMvc = MockMvcBuilders.standaloneSetup(teamSkillResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamSkill createEntity(EntityManager em) {
        TeamSkill teamSkill = new TeamSkill()
            .completedAt(DEFAULT_COMPLETED_AT)
            .verifiedAt(DEFAULT_VERIFIED_AT)
            .irrelevant(DEFAULT_IRRELEVANT)
            .note(DEFAULT_NOTE)
            .vote(DEFAULT_VOTE)
            .voters(DEFAULT_VOTERS);
        // Add required entity
        Skill skill = SkillResourceIntTest.createEntity(em);
        em.persist(skill);
        em.flush();
        teamSkill.setSkill(skill);
        // Add required entity
        Team team = TeamResourceIntTest.createEntity(em);
        em.persist(team);
        em.flush();
        teamSkill.setTeam(team);
        return teamSkill;
    }

    @Before
    public void initTest() {
        teamSkill = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeamSkill() throws Exception {
        int databaseSizeBeforeCreate = teamSkillRepository.findAll().size();

        // Create the TeamSkill
        TeamSkillDTO teamSkillDTO = teamSkillMapper.toDto(teamSkill);
        restTeamSkillMockMvc.perform(post("/api/team-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamSkillDTO)))
            .andExpect(status().isCreated());

        // Validate the TeamSkill in the database
        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeCreate + 1);
        TeamSkill testTeamSkill = teamSkillList.get(teamSkillList.size() - 1);
        assertThat(testTeamSkill.getCompletedAt()).isEqualTo(DEFAULT_COMPLETED_AT);
        assertThat(testTeamSkill.getVerifiedAt()).isEqualTo(DEFAULT_VERIFIED_AT);
        assertThat(testTeamSkill.isIrrelevant()).isEqualTo(DEFAULT_IRRELEVANT);
        assertThat(testTeamSkill.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testTeamSkill.getVote()).isEqualTo(DEFAULT_VOTE);
        assertThat(testTeamSkill.getVoters()).isEqualTo(DEFAULT_VOTERS);
    }

    @Test
    @Transactional
    public void createTeamSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teamSkillRepository.findAll().size();

        // Create the TeamSkill with an existing ID
        teamSkill.setId(1L);
        TeamSkillDTO teamSkillDTO = teamSkillMapper.toDto(teamSkill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamSkillMockMvc.perform(post("/api/team-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TeamSkill in the database
        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkVoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = teamSkillRepository.findAll().size();
        // set the field null
        teamSkill.setVote(null);

        // Create the TeamSkill, which fails.
        TeamSkillDTO teamSkillDTO = teamSkillMapper.toDto(teamSkill);

        restTeamSkillMockMvc.perform(post("/api/team-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamSkillDTO)))
            .andExpect(status().isBadRequest());

        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTeamSkills() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList
        restTeamSkillMockMvc.perform(get("/api/team-skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].completedAt").value(hasItem(DEFAULT_COMPLETED_AT.toString())))
            .andExpect(jsonPath("$.[*].verifiedAt").value(hasItem(DEFAULT_VERIFIED_AT.toString())))
            .andExpect(jsonPath("$.[*].irrelevant").value(hasItem(DEFAULT_IRRELEVANT.booleanValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].vote").value(hasItem(DEFAULT_VOTE)))
            .andExpect(jsonPath("$.[*].voters").value(hasItem(DEFAULT_VOTERS.toString())));
    }
    

    @Test
    @Transactional
    public void getTeamSkill() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get the teamSkill
        restTeamSkillMockMvc.perform(get("/api/team-skills/{id}", teamSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teamSkill.getId().intValue()))
            .andExpect(jsonPath("$.completedAt").value(DEFAULT_COMPLETED_AT.toString()))
            .andExpect(jsonPath("$.verifiedAt").value(DEFAULT_VERIFIED_AT.toString()))
            .andExpect(jsonPath("$.irrelevant").value(DEFAULT_IRRELEVANT.booleanValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.vote").value(DEFAULT_VOTE))
            .andExpect(jsonPath("$.voters").value(DEFAULT_VOTERS.toString()));
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByCompletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where completedAt equals to DEFAULT_COMPLETED_AT
        defaultTeamSkillShouldBeFound("completedAt.equals=" + DEFAULT_COMPLETED_AT);

        // Get all the teamSkillList where completedAt equals to UPDATED_COMPLETED_AT
        defaultTeamSkillShouldNotBeFound("completedAt.equals=" + UPDATED_COMPLETED_AT);
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByCompletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where completedAt in DEFAULT_COMPLETED_AT or UPDATED_COMPLETED_AT
        defaultTeamSkillShouldBeFound("completedAt.in=" + DEFAULT_COMPLETED_AT + "," + UPDATED_COMPLETED_AT);

        // Get all the teamSkillList where completedAt equals to UPDATED_COMPLETED_AT
        defaultTeamSkillShouldNotBeFound("completedAt.in=" + UPDATED_COMPLETED_AT);
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByCompletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where completedAt is not null
        defaultTeamSkillShouldBeFound("completedAt.specified=true");

        // Get all the teamSkillList where completedAt is null
        defaultTeamSkillShouldNotBeFound("completedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByVerifiedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where verifiedAt equals to DEFAULT_VERIFIED_AT
        defaultTeamSkillShouldBeFound("verifiedAt.equals=" + DEFAULT_VERIFIED_AT);

        // Get all the teamSkillList where verifiedAt equals to UPDATED_VERIFIED_AT
        defaultTeamSkillShouldNotBeFound("verifiedAt.equals=" + UPDATED_VERIFIED_AT);
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByVerifiedAtIsInShouldWork() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where verifiedAt in DEFAULT_VERIFIED_AT or UPDATED_VERIFIED_AT
        defaultTeamSkillShouldBeFound("verifiedAt.in=" + DEFAULT_VERIFIED_AT + "," + UPDATED_VERIFIED_AT);

        // Get all the teamSkillList where verifiedAt equals to UPDATED_VERIFIED_AT
        defaultTeamSkillShouldNotBeFound("verifiedAt.in=" + UPDATED_VERIFIED_AT);
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByVerifiedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where verifiedAt is not null
        defaultTeamSkillShouldBeFound("verifiedAt.specified=true");

        // Get all the teamSkillList where verifiedAt is null
        defaultTeamSkillShouldNotBeFound("verifiedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByIrrelevantIsEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where irrelevant equals to DEFAULT_IRRELEVANT
        defaultTeamSkillShouldBeFound("irrelevant.equals=" + DEFAULT_IRRELEVANT);

        // Get all the teamSkillList where irrelevant equals to UPDATED_IRRELEVANT
        defaultTeamSkillShouldNotBeFound("irrelevant.equals=" + UPDATED_IRRELEVANT);
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByIrrelevantIsInShouldWork() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where irrelevant in DEFAULT_IRRELEVANT or UPDATED_IRRELEVANT
        defaultTeamSkillShouldBeFound("irrelevant.in=" + DEFAULT_IRRELEVANT + "," + UPDATED_IRRELEVANT);

        // Get all the teamSkillList where irrelevant equals to UPDATED_IRRELEVANT
        defaultTeamSkillShouldNotBeFound("irrelevant.in=" + UPDATED_IRRELEVANT);
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByIrrelevantIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where irrelevant is not null
        defaultTeamSkillShouldBeFound("irrelevant.specified=true");

        // Get all the teamSkillList where irrelevant is null
        defaultTeamSkillShouldNotBeFound("irrelevant.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where note equals to DEFAULT_NOTE
        defaultTeamSkillShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the teamSkillList where note equals to UPDATED_NOTE
        defaultTeamSkillShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultTeamSkillShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the teamSkillList where note equals to UPDATED_NOTE
        defaultTeamSkillShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where note is not null
        defaultTeamSkillShouldBeFound("note.specified=true");

        // Get all the teamSkillList where note is null
        defaultTeamSkillShouldNotBeFound("note.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByVoteIsEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where vote equals to DEFAULT_VOTE
        defaultTeamSkillShouldBeFound("vote.equals=" + DEFAULT_VOTE);

        // Get all the teamSkillList where vote equals to UPDATED_VOTE
        defaultTeamSkillShouldNotBeFound("vote.equals=" + UPDATED_VOTE);
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByVoteIsInShouldWork() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where vote in DEFAULT_VOTE or UPDATED_VOTE
        defaultTeamSkillShouldBeFound("vote.in=" + DEFAULT_VOTE + "," + UPDATED_VOTE);

        // Get all the teamSkillList where vote equals to UPDATED_VOTE
        defaultTeamSkillShouldNotBeFound("vote.in=" + UPDATED_VOTE);
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByVoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where vote is not null
        defaultTeamSkillShouldBeFound("vote.specified=true");

        // Get all the teamSkillList where vote is null
        defaultTeamSkillShouldNotBeFound("vote.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByVoteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where vote greater than or equals to DEFAULT_VOTE
        defaultTeamSkillShouldBeFound("vote.greaterOrEqualThan=" + DEFAULT_VOTE);

        // Get all the teamSkillList where vote greater than or equals to UPDATED_VOTE
        defaultTeamSkillShouldNotBeFound("vote.greaterOrEqualThan=" + UPDATED_VOTE);
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByVoteIsLessThanSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where vote less than or equals to DEFAULT_VOTE
        defaultTeamSkillShouldNotBeFound("vote.lessThan=" + DEFAULT_VOTE);

        // Get all the teamSkillList where vote less than or equals to UPDATED_VOTE
        defaultTeamSkillShouldBeFound("vote.lessThan=" + UPDATED_VOTE);
    }


    @Test
    @Transactional
    public void getAllTeamSkillsByVotersIsEqualToSomething() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where voters equals to DEFAULT_VOTERS
        defaultTeamSkillShouldBeFound("voters.equals=" + DEFAULT_VOTERS);

        // Get all the teamSkillList where voters equals to UPDATED_VOTERS
        defaultTeamSkillShouldNotBeFound("voters.equals=" + UPDATED_VOTERS);
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByVotersIsInShouldWork() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where voters in DEFAULT_VOTERS or UPDATED_VOTERS
        defaultTeamSkillShouldBeFound("voters.in=" + DEFAULT_VOTERS + "," + UPDATED_VOTERS);

        // Get all the teamSkillList where voters equals to UPDATED_VOTERS
        defaultTeamSkillShouldNotBeFound("voters.in=" + UPDATED_VOTERS);
    }

    @Test
    @Transactional
    public void getAllTeamSkillsByVotersIsNullOrNotNull() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        // Get all the teamSkillList where voters is not null
        defaultTeamSkillShouldBeFound("voters.specified=true");

        // Get all the teamSkillList where voters is null
        defaultTeamSkillShouldNotBeFound("voters.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeamSkillsBySkillIsEqualToSomething() throws Exception {
        // Initialize the database
        Skill skill = SkillResourceIntTest.createEntity(em);
        em.persist(skill);
        em.flush();
        teamSkill.setSkill(skill);
        teamSkillRepository.saveAndFlush(teamSkill);
        Long skillId = skill.getId();

        // Get all the teamSkillList where skill equals to skillId
        defaultTeamSkillShouldBeFound("skillId.equals=" + skillId);

        // Get all the teamSkillList where skill equals to skillId + 1
        defaultTeamSkillShouldNotBeFound("skillId.equals=" + (skillId + 1));
    }


    @Test
    @Transactional
    public void getAllTeamSkillsByTeamIsEqualToSomething() throws Exception {
        // Initialize the database
        Team team = TeamResourceIntTest.createEntity(em);
        em.persist(team);
        em.flush();
        teamSkill.setTeam(team);
        teamSkillRepository.saveAndFlush(teamSkill);
        Long teamId = team.getId();

        // Get all the teamSkillList where team equals to teamId
        defaultTeamSkillShouldBeFound("teamId.equals=" + teamId);

        // Get all the teamSkillList where team equals to teamId + 1
        defaultTeamSkillShouldNotBeFound("teamId.equals=" + (teamId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTeamSkillShouldBeFound(String filter) throws Exception {
        restTeamSkillMockMvc.perform(get("/api/team-skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].completedAt").value(hasItem(DEFAULT_COMPLETED_AT.toString())))
            .andExpect(jsonPath("$.[*].verifiedAt").value(hasItem(DEFAULT_VERIFIED_AT.toString())))
            .andExpect(jsonPath("$.[*].irrelevant").value(hasItem(DEFAULT_IRRELEVANT.booleanValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].vote").value(hasItem(DEFAULT_VOTE)))
            .andExpect(jsonPath("$.[*].voters").value(hasItem(DEFAULT_VOTERS.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTeamSkillShouldNotBeFound(String filter) throws Exception {
        restTeamSkillMockMvc.perform(get("/api/team-skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingTeamSkill() throws Exception {
        // Get the teamSkill
        restTeamSkillMockMvc.perform(get("/api/team-skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeamSkill() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        int databaseSizeBeforeUpdate = teamSkillRepository.findAll().size();

        // Update the teamSkill
        TeamSkill updatedTeamSkill = teamSkillRepository.findById(teamSkill.getId()).get();
        // Disconnect from session so that the updates on updatedTeamSkill are not directly saved in db
        em.detach(updatedTeamSkill);
        updatedTeamSkill
            .completedAt(UPDATED_COMPLETED_AT)
            .verifiedAt(UPDATED_VERIFIED_AT)
            .irrelevant(UPDATED_IRRELEVANT)
            .note(UPDATED_NOTE)
            .vote(UPDATED_VOTE)
            .voters(UPDATED_VOTERS);
        TeamSkillDTO teamSkillDTO = teamSkillMapper.toDto(updatedTeamSkill);

        restTeamSkillMockMvc.perform(put("/api/team-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamSkillDTO)))
            .andExpect(status().isOk());

        // Validate the TeamSkill in the database
        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeUpdate);
        TeamSkill testTeamSkill = teamSkillList.get(teamSkillList.size() - 1);
        assertThat(testTeamSkill.getCompletedAt()).isEqualTo(UPDATED_COMPLETED_AT);
        assertThat(testTeamSkill.getVerifiedAt()).isEqualTo(UPDATED_VERIFIED_AT);
        assertThat(testTeamSkill.isIrrelevant()).isEqualTo(UPDATED_IRRELEVANT);
        assertThat(testTeamSkill.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testTeamSkill.getVote()).isEqualTo(UPDATED_VOTE);
        assertThat(testTeamSkill.getVoters()).isEqualTo(UPDATED_VOTERS);
    }

    @Test
    @Transactional
    public void updateNonExistingTeamSkill() throws Exception {
        int databaseSizeBeforeUpdate = teamSkillRepository.findAll().size();

        // Create the TeamSkill
        TeamSkillDTO teamSkillDTO = teamSkillMapper.toDto(teamSkill);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTeamSkillMockMvc.perform(put("/api/team-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teamSkillDTO)))
            .andExpect(status().isCreated());

        // Validate the TeamSkill in the database
        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTeamSkill() throws Exception {
        // Initialize the database
        teamSkillRepository.saveAndFlush(teamSkill);

        int databaseSizeBeforeDelete = teamSkillRepository.findAll().size();

        // Get the teamSkill
        restTeamSkillMockMvc.perform(delete("/api/team-skills/{id}", teamSkill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TeamSkill> teamSkillList = teamSkillRepository.findAll();
        assertThat(teamSkillList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamSkill.class);
        TeamSkill teamSkill1 = new TeamSkill();
        teamSkill1.setId(1L);
        TeamSkill teamSkill2 = new TeamSkill();
        teamSkill2.setId(teamSkill1.getId());
        assertThat(teamSkill1).isEqualTo(teamSkill2);
        teamSkill2.setId(2L);
        assertThat(teamSkill1).isNotEqualTo(teamSkill2);
        teamSkill1.setId(null);
        assertThat(teamSkill1).isNotEqualTo(teamSkill2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamSkillDTO.class);
        TeamSkillDTO teamSkillDTO1 = new TeamSkillDTO();
        teamSkillDTO1.setId(1L);
        TeamSkillDTO teamSkillDTO2 = new TeamSkillDTO();
        assertThat(teamSkillDTO1).isNotEqualTo(teamSkillDTO2);
        teamSkillDTO2.setId(teamSkillDTO1.getId());
        assertThat(teamSkillDTO1).isEqualTo(teamSkillDTO2);
        teamSkillDTO2.setId(2L);
        assertThat(teamSkillDTO1).isNotEqualTo(teamSkillDTO2);
        teamSkillDTO1.setId(null);
        assertThat(teamSkillDTO1).isNotEqualTo(teamSkillDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(teamSkillMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(teamSkillMapper.fromId(null)).isNull();
    }
}
