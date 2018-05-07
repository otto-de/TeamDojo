package de.otto.teamdojo.web.rest;

import de.otto.teamdojo.TeamdojoApp;
import de.otto.teamdojo.domain.*;
import de.otto.teamdojo.repository.TeamSkillRepository;
import de.otto.teamdojo.service.AchievableSkillService;
import de.otto.teamdojo.service.dto.AchievableSkillDTO;
import de.otto.teamdojo.web.rest.errors.ExceptionTranslator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.util.List;

import static de.otto.teamdojo.test.util.BadgeTestDataProvider.alwaysUpToDate;
import static de.otto.teamdojo.test.util.BadgeTestDataProvider.awsReady;
import static de.otto.teamdojo.test.util.DimensionTestDataProvider.security;
import static de.otto.teamdojo.test.util.LevelTestDataProvider.orange;
import static de.otto.teamdojo.test.util.LevelTestDataProvider.yellow;
import static de.otto.teamdojo.test.util.SkillTestDataProvider.*;
import static de.otto.teamdojo.test.util.TeamTestDataProvider.ft1;
import static de.otto.teamdojo.web.rest.TestUtil.createFormattingConversionService;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TeamdojoApp.class)
public class TeamAchievableSkillResourceIntTest {

    @Autowired
    private AchievableSkillService achievableSkillService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private TeamSkillRepository teamSkillRepository;

    private MockMvc restTeamMockMvc;

    private Team team;
    private Skill inputValidation;
    private Skill softwareUpdates;
    private Skill strongPasswords;
    private Skill dockerized;
    private Level yellow;
    private Level orange;
    private Dimension security;
    private TeamSkill teamSkill;
    private Badge awsReady;
    private Badge alwaysUpToDate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeamAchievableSkillResource resource = new TeamAchievableSkillResource(achievableSkillService);
        this.restTeamMockMvc = MockMvcBuilders.standaloneSetup(resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @After
    public void tearDown() {
        team = null;
        inputValidation = null;
        softwareUpdates = null;
        strongPasswords = null;
        dockerized = null;
        yellow = null;
        orange = null;
        security = null;
        teamSkill = null;
        awsReady = null;
        alwaysUpToDate = null;
    }

    @Test
    @Transactional
    public void getAllAchievableSkills() throws Exception {
        setupTestData();
        em.flush();

        restTeamMockMvc.perform(get("/api/teams/{id}/achievable-skills", team.getId()))
            // no level parameter is send
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.length()").value(4))
            .andExpect(jsonPath("$.[*].teamSkillId").value(containsInAnyOrder(
                null,
                null,
                null,
                teamSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(containsInAnyOrder(
                INPUT_VALIDATION_TITLE,
                SOFTWARE_UPDATES_TITLE,
                STRONG_PASSWORDS_TITLE,
                DOCKERIZED_TITLE)
            ));
    }

    @Test
    @Transactional
    public void getAchievableSkillsByLevels() throws Exception {
        setupTestData();
        em.flush();

        restTeamMockMvc.perform(get("/api/teams/{id}/achievable-skills", team.getId())
            .param("levelId", yellow.getId().toString(), orange.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.length()").value(3))
            .andExpect(jsonPath("$.[*].teamSkillId").value(containsInAnyOrder(
                null,
                null,
                teamSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].skillId").value(containsInAnyOrder(
                inputValidation.getId().intValue(),
                softwareUpdates.getId().intValue(),
                strongPasswords.getId().intValue()
            )));
    }

    @Test
    @Transactional
    public void getAchievableSkillsByLevel() throws Exception {
        setupTestData();
        em.flush();

        restTeamMockMvc.perform(get("/api/teams/{id}/achievable-skills", team.getId())
            .param("levelId", yellow.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$.[*].teamSkillId").value(containsInAnyOrder(
                null,
                teamSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].skillId").value(containsInAnyOrder(
                inputValidation.getId().intValue(),
                softwareUpdates.getId().intValue())
            ));
    }

    @Test
    @Transactional
    public void getAchievableSkillsByBadges() throws Exception {
        setupTestData();
        em.flush();

        restTeamMockMvc.perform(get("/api/teams/{id}/achievable-skills", team.getId())
            .param("badgeId", awsReady.getId().toString(), alwaysUpToDate.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.length()").value(3))
            .andExpect(jsonPath("$.[*].teamSkillId").value(containsInAnyOrder(
                null,
                null,
                teamSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].skillId").value(containsInAnyOrder(
                inputValidation.getId().intValue(),
                dockerized.getId().intValue(),
                softwareUpdates.getId().intValue()
            )));
    }

    @Test
    @Transactional
    public void getAchievableSkillsByBadge() throws Exception {
        setupTestData();
        em.flush();

        restTeamMockMvc.perform(get("/api/teams/{id}/achievable-skills", team.getId())
            .param("badgeId", awsReady.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$.[*].teamSkillId").value(containsInAnyOrder(
                null,
                teamSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].skillId").value(containsInAnyOrder(
                inputValidation.getId().intValue(),
                dockerized.getId().intValue()
            )));
    }

    @Test
    @Transactional
    public void getAchievableSkillsByLevelAndBadge() throws Exception {
        setupTestData();
        em.flush();

        restTeamMockMvc.perform(get("/api/teams/{id}/achievable-skills", team.getId())
            .param("levelId", yellow.getId().toString())
            .param("badgeId", awsReady.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.length()").value(3))
            .andExpect(jsonPath("$.[*].teamSkillId").value(containsInAnyOrder(
                null,
                null,
                teamSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].skillId").value(containsInAnyOrder(
                inputValidation.getId().intValue(),
                softwareUpdates.getId().intValue(),
                dockerized.getId().intValue()
            )));
    }

    @Test
    @Transactional
    public void getAchievableSkillsTeamNotFound() throws Exception {
        Long unknownTeamId = 123L;
        restTeamMockMvc.perform(get("/api/teams/{id}/achievable-skills", unknownTeamId))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void getAchievableSkillsWhenNoSkillsExist() throws Exception {
        team = ft1().build(em);

        restTeamMockMvc.perform(get("/api/teams/{id}/achievable-skills", team.getId()))
            .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void updateAchievableSkillToCompleted() throws Exception {
        softwareUpdates = softwareUpdates().build(em);
        team = ft1().build(em);
        em.flush();

        AchievableSkillDTO achievableSkill = new AchievableSkillDTO();
        achievableSkill.setAchievedAt(Instant.now());
        achievableSkill.setSkillId(softwareUpdates.getId());

        restTeamMockMvc.perform(put("/api/teams/{id}/achievable-skills", team.getId())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievableSkill)))
            .andExpect(status().isOk());

        List<TeamSkill> teamSkills = teamSkillRepository.findAll();
        assertThat(teamSkills, hasSize(1));
        TeamSkill completedSkill = teamSkills.get(0);
        assertThat(completedSkill.getSkill().getId(), is(softwareUpdates.getId()));
        assertThat(completedSkill.getTeam().getId(), is(team.getId()));
        assertThat(completedSkill.getAchievedAt(), notNullValue());
    }


    @Test
    @Transactional
    public void updateAchievableSkillToInCompleted() throws Exception {
        softwareUpdates = softwareUpdates().build(em);
        team = ft1().build(em);
        teamSkill = new TeamSkill();
        teamSkill.setTeam(team);
        teamSkill.setSkill(softwareUpdates);
        em.persist(teamSkill);
        team.addSkills(teamSkill);
        em.persist(team);
        em.flush();

        AchievableSkillDTO achievableSkill = new AchievableSkillDTO();
        achievableSkill.setAchievedAt(null);
        achievableSkill.setTeamSkillId(teamSkill.getId());
        achievableSkill.setSkillId(softwareUpdates.getId());

        restTeamMockMvc.perform(put("/api/teams/{id}/achievable-skills", team.getId())
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievableSkill)))
            .andExpect(status().isOk());

        List<TeamSkill> teamSkills = teamSkillRepository.findAll();
        assertThat(teamSkills, hasSize(1));
        TeamSkill completedSkill = teamSkills.get(0);
        assertThat(completedSkill.getSkill().getId(), is(softwareUpdates.getId()));
        assertThat(completedSkill.getTeam().getId(), is(team.getId()));
        assertThat(completedSkill.getAchievedAt(), nullValue());
    }

    private void setupTestData() {
        inputValidation = inputValidation().build(em);
        softwareUpdates = softwareUpdates().build(em);
        strongPasswords = strongPasswords().build(em);
        dockerized = dockerized().build(em);
        Skill evilUserStories_notAchievable = evilUserStories().build(em);

        security = security().build(em);

        yellow = yellow(security).addSkill(inputValidation).addSkill(softwareUpdates).build(em);
        orange = orange(security).addSkill(strongPasswords).dependsOn(yellow).build(em);

        awsReady = awsReady().addDimension(security).addSkill(inputValidation).addSkill(dockerized).build(em);
        alwaysUpToDate = alwaysUpToDate().addSkill(softwareUpdates).build(em);

        team = ft1().build(em);
        team.addParticipations(security);
        em.persist(team);
        teamSkill = new TeamSkill();
        teamSkill.setTeam(team);
        teamSkill.setSkill(inputValidation);
        em.persist(teamSkill);
        team.addSkills(teamSkill);
        em.persist(team);
    }
}
