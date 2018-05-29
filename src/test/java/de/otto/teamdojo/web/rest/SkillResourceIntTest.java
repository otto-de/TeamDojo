package de.otto.teamdojo.web.rest;

import de.otto.teamdojo.TeamdojoApp;
import de.otto.teamdojo.domain.BadgeSkill;
import de.otto.teamdojo.domain.LevelSkill;
import de.otto.teamdojo.domain.Skill;
import de.otto.teamdojo.domain.TeamSkill;
import de.otto.teamdojo.repository.SkillRepository;
import de.otto.teamdojo.service.SkillQueryService;
import de.otto.teamdojo.service.SkillService;
import de.otto.teamdojo.service.dto.SkillDTO;
import de.otto.teamdojo.service.mapper.SkillMapper;
import de.otto.teamdojo.web.rest.errors.ExceptionTranslator;
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
import java.util.List;

import static de.otto.teamdojo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SkillResource REST controller.
 *
 * @see SkillResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TeamdojoApp.class)
public class SkillResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMPLEMENTATION = "AAAAAAAAAA";
    private static final String UPDATED_IMPLEMENTATION = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDATION = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION = "BBBBBBBBBB";

    private static final String DEFAULT_EXPIRY_PERIOD = "P+5M+24D";
    private static final String UPDATED_EXPIRY_PERIOD = "P78Y3W";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCORE = 0;
    private static final Integer UPDATED_SCORE = 1;

    private static final Double DEFAULT_RATE_SCORE = 0D;
    private static final Double UPDATED_RATE_SCORE = 1D;

    private static final Integer DEFAULT_RATE_COUNT = 0;
    private static final Integer UPDATED_RATE_COUNT = 1;

    @Autowired
    private SkillRepository skillRepository;



    @Autowired
    private SkillMapper skillMapper;


    @Autowired
    private SkillService skillService;

    @Autowired
    private SkillQueryService skillQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSkillMockMvc;

    private Skill skill;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkillResource skillResource = new SkillResource(skillService, skillQueryService);
        this.restSkillMockMvc = MockMvcBuilders.standaloneSetup(skillResource)
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
    public static Skill createEntity(EntityManager em) {
        Skill skill = new Skill()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .implementation(DEFAULT_IMPLEMENTATION)
            .validation(DEFAULT_VALIDATION)
            .expiryPeriod(DEFAULT_EXPIRY_PERIOD)
            .contact(DEFAULT_CONTACT)
            .score(DEFAULT_SCORE)
            .rateScore(DEFAULT_RATE_SCORE)
            .rateCount(DEFAULT_RATE_COUNT);
        return skill;
    }

    @Before
    public void initTest() {
        skill = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkill() throws Exception {
        int databaseSizeBeforeCreate = skillRepository.findAll().size();

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);
        restSkillMockMvc.perform(post("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isCreated());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeCreate + 1);
        Skill testSkill = skillList.get(skillList.size() - 1);
        assertThat(testSkill.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSkill.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSkill.getImplementation()).isEqualTo(DEFAULT_IMPLEMENTATION);
        assertThat(testSkill.getValidation()).isEqualTo(DEFAULT_VALIDATION);
        assertThat(testSkill.getExpiryPeriod()).isEqualTo(DEFAULT_EXPIRY_PERIOD);
        assertThat(testSkill.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testSkill.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testSkill.getRateScore()).isEqualTo(DEFAULT_RATE_SCORE);
        assertThat(testSkill.getRateCount()).isEqualTo(DEFAULT_RATE_COUNT);
    }

    @Test
    @Transactional
    public void createSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillRepository.findAll().size();

        // Create the Skill with an existing ID
        skill.setId(1L);
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillMockMvc.perform(post("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillRepository.findAll().size();
        // set the field null
        skill.setTitle(null);

        // Create the Skill, which fails.
        SkillDTO skillDTO = skillMapper.toDto(skill);

        restSkillMockMvc.perform(post("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isBadRequest());

        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillRepository.findAll().size();
        // set the field null
        skill.setScore(null);

        // Create the Skill, which fails.
        SkillDTO skillDTO = skillMapper.toDto(skill);

        restSkillMockMvc.perform(post("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isBadRequest());

        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSkills() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList
        restSkillMockMvc.perform(get("/api/skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skill.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].implementation").value(hasItem(DEFAULT_IMPLEMENTATION.toString())))
            .andExpect(jsonPath("$.[*].validation").value(hasItem(DEFAULT_VALIDATION.toString())))
            .andExpect(jsonPath("$.[*].expiryPeriod").value(hasItem(DEFAULT_EXPIRY_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].rateScore").value(hasItem(DEFAULT_RATE_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].rateCount").value(hasItem(DEFAULT_RATE_COUNT)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)));
    }


    @Test
    @Transactional
    public void getSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get the skill
        restSkillMockMvc.perform(get("/api/skills/{id}", skill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skill.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.implementation").value(DEFAULT_IMPLEMENTATION.toString()))
            .andExpect(jsonPath("$.validation").value(DEFAULT_VALIDATION.toString()))
            .andExpect(jsonPath("$.expiryPeriod").value(DEFAULT_EXPIRY_PERIOD.toString()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()))
            .andExpect(jsonPath("$.rateScore").value(DEFAULT_RATE_SCORE.doubleValue()))
            .andExpect(jsonPath("$.rateCount").value(DEFAULT_RATE_COUNT));
    }

    @Test
    @Transactional
    public void getAllSkillsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where title equals to DEFAULT_TITLE
        defaultSkillShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the skillList where title equals to UPDATED_TITLE
        defaultSkillShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllSkillsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultSkillShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the skillList where title equals to UPDATED_TITLE
        defaultSkillShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllSkillsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where title is not null
        defaultSkillShouldBeFound("title.specified=true");

        // Get all the skillList where title is null
        defaultSkillShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllSkillsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where description equals to DEFAULT_DESCRIPTION
        defaultSkillShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the skillList where description equals to UPDATED_DESCRIPTION
        defaultSkillShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSkillsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSkillShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the skillList where description equals to UPDATED_DESCRIPTION
        defaultSkillShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSkillsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where description is not null
        defaultSkillShouldBeFound("description.specified=true");

        // Get all the skillList where description is null
        defaultSkillShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllSkillsByImplementationIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementation equals to DEFAULT_IMPLEMENTATION
        defaultSkillShouldBeFound("implementation.equals=" + DEFAULT_IMPLEMENTATION);

        // Get all the skillList where implementation equals to UPDATED_IMPLEMENTATION
        defaultSkillShouldNotBeFound("implementation.equals=" + UPDATED_IMPLEMENTATION);
    }

    @Test
    @Transactional
    public void getAllSkillsByImplementationIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementation in DEFAULT_IMPLEMENTATION or UPDATED_IMPLEMENTATION
        defaultSkillShouldBeFound("implementation.in=" + DEFAULT_IMPLEMENTATION + "," + UPDATED_IMPLEMENTATION);

        // Get all the skillList where implementation equals to UPDATED_IMPLEMENTATION
        defaultSkillShouldNotBeFound("implementation.in=" + UPDATED_IMPLEMENTATION);
    }

    @Test
    @Transactional
    public void getAllSkillsByImplementationIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where implementation is not null
        defaultSkillShouldBeFound("implementation.specified=true");

        // Get all the skillList where implementation is null
        defaultSkillShouldNotBeFound("implementation.specified=false");
    }

    @Test
    @Transactional
    public void getAllSkillsByValidationIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validation equals to DEFAULT_VALIDATION
        defaultSkillShouldBeFound("validation.equals=" + DEFAULT_VALIDATION);

        // Get all the skillList where validation equals to UPDATED_VALIDATION
        defaultSkillShouldNotBeFound("validation.equals=" + UPDATED_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllSkillsByValidationIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validation in DEFAULT_VALIDATION or UPDATED_VALIDATION
        defaultSkillShouldBeFound("validation.in=" + DEFAULT_VALIDATION + "," + UPDATED_VALIDATION);

        // Get all the skillList where validation equals to UPDATED_VALIDATION
        defaultSkillShouldNotBeFound("validation.in=" + UPDATED_VALIDATION);
    }

    @Test
    @Transactional
    public void getAllSkillsByValidationIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where validation is not null
        defaultSkillShouldBeFound("validation.specified=true");

        // Get all the skillList where validation is null
        defaultSkillShouldNotBeFound("validation.specified=false");
    }

    @Test
    @Transactional
    public void getAllSkillsByExpiryPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where expiryPeriod equals to DEFAULT_EXPIRY_PERIOD
        defaultSkillShouldBeFound("expiryPeriod.equals=" + DEFAULT_EXPIRY_PERIOD);

        // Get all the skillList where expiryPeriod equals to UPDATED_EXPIRY_PERIOD
        defaultSkillShouldNotBeFound("expiryPeriod.equals=" + UPDATED_EXPIRY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllSkillsByExpiryPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where expiryPeriod in DEFAULT_EXPIRY_PERIOD or UPDATED_EXPIRY_PERIOD
        defaultSkillShouldBeFound("expiryPeriod.in=" + DEFAULT_EXPIRY_PERIOD + "," + UPDATED_EXPIRY_PERIOD);

        // Get all the skillList where expiryPeriod equals to UPDATED_EXPIRY_PERIOD
        defaultSkillShouldNotBeFound("expiryPeriod.in=" + UPDATED_EXPIRY_PERIOD);
    }

    @Test
    @Transactional
    public void getAllSkillsByExpiryPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where expiryPeriod is not null
        defaultSkillShouldBeFound("expiryPeriod.specified=true");

        // Get all the skillList where expiryPeriod is null
        defaultSkillShouldNotBeFound("expiryPeriod.specified=false");
    }

    @Test
    @Transactional
    public void getAllSkillsByContactIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where contact equals to DEFAULT_CONTACT
        defaultSkillShouldBeFound("contact.equals=" + DEFAULT_CONTACT);

        // Get all the skillList where contact equals to UPDATED_CONTACT
        defaultSkillShouldNotBeFound("contact.equals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllSkillsByContactIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where contact in DEFAULT_CONTACT or UPDATED_CONTACT
        defaultSkillShouldBeFound("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT);

        // Get all the skillList where contact equals to UPDATED_CONTACT
        defaultSkillShouldNotBeFound("contact.in=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllSkillsByContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where contact is not null
        defaultSkillShouldBeFound("contact.specified=true");

        // Get all the skillList where contact is null
        defaultSkillShouldNotBeFound("contact.specified=false");
    }

    @Test
    @Transactional
    public void getAllSkillsByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where score equals to DEFAULT_SCORE
        defaultSkillShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the skillList where score equals to UPDATED_SCORE
        defaultSkillShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllSkillsByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultSkillShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the skillList where score equals to UPDATED_SCORE
        defaultSkillShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllSkillsByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where score is not null
        defaultSkillShouldBeFound("score.specified=true");

        // Get all the skillList where score is null
        defaultSkillShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    public void getAllSkillsByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where score greater than or equals to DEFAULT_SCORE
        defaultSkillShouldBeFound("score.greaterOrEqualThan=" + DEFAULT_SCORE);

        // Get all the skillList where score greater than or equals to UPDATED_SCORE
        defaultSkillShouldNotBeFound("score.greaterOrEqualThan=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllSkillsByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where score less than or equals to DEFAULT_SCORE
        defaultSkillShouldNotBeFound("score.lessThan=" + DEFAULT_SCORE);

        // Get all the skillList where score less than or equals to UPDATED_SCORE
        defaultSkillShouldBeFound("score.lessThan=" + UPDATED_SCORE);
    }


    @Test @Transactional public void getAllSkillsByRateScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateScore equals to DEFAULT_RATE_SCORE
        defaultSkillShouldBeFound("rateScore.equals=" + DEFAULT_RATE_SCORE);

        // Get all the skillList where rateScore equals to UPDATED_RATE_SCORE
        defaultSkillShouldNotBeFound("rateScore.equals=" + UPDATED_RATE_SCORE);
    }

    @Test @Transactional public void getAllSkillsByRateScoreIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateScore in DEFAULT_RATE_SCORE or UPDATED_RATE_SCORE
        defaultSkillShouldBeFound("rateScore.in=" + DEFAULT_RATE_SCORE + "," + UPDATED_RATE_SCORE);

        // Get all the skillList where rateScore equals to UPDATED_RATE_SCORE
        defaultSkillShouldNotBeFound("rateScore.in=" + UPDATED_RATE_SCORE);
    }

    @Test @Transactional public void getAllSkillsByRateScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateScore is not null
        defaultSkillShouldBeFound("rateScore.specified=true");

        // Get all the skillList where rateScore is null
        defaultSkillShouldNotBeFound("rateScore.specified=false");
    }

    @Test @Transactional public void getAllSkillsByRateCountIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateCount equals to DEFAULT_RATE_COUNT
        defaultSkillShouldBeFound("rateCount.equals=" + DEFAULT_RATE_COUNT);

        // Get all the skillList where rateCount equals to UPDATED_RATE_COUNT
        defaultSkillShouldNotBeFound("rateCount.equals=" + UPDATED_RATE_COUNT);
    }

    @Test @Transactional public void getAllSkillsByRateCountIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateCount in DEFAULT_RATE_COUNT or UPDATED_RATE_COUNT
        defaultSkillShouldBeFound("rateCount.in=" + DEFAULT_RATE_COUNT + "," + UPDATED_RATE_COUNT);

        // Get all the skillList where rateCount equals to UPDATED_RATE_COUNT
        defaultSkillShouldNotBeFound("rateCount.in=" + UPDATED_RATE_COUNT);
    }

    @Test @Transactional public void getAllSkillsByRateCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateCount is not null
        defaultSkillShouldBeFound("rateCount.specified=true");

        // Get all the skillList where rateCount is null
        defaultSkillShouldNotBeFound("rateCount.specified=false");
    }

    @Test @Transactional public void getAllSkillsByRateCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateCount greater than or equals to DEFAULT_RATE_COUNT
        defaultSkillShouldBeFound("rateCount.greaterOrEqualThan=" + DEFAULT_RATE_COUNT);

        // Get all the skillList where rateCount greater than or equals to UPDATED_RATE_COUNT
        defaultSkillShouldNotBeFound("rateCount.greaterOrEqualThan=" + UPDATED_RATE_COUNT);
    }

    @Test @Transactional public void getAllSkillsByRateCountIsLessThanSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where rateCount less than or equals to DEFAULT_RATE_COUNT
        defaultSkillShouldNotBeFound("rateCount.lessThan=" + DEFAULT_RATE_COUNT);

        // Get all the skillList where rateCount less than or equals to UPDATED_RATE_COUNT
        defaultSkillShouldBeFound("rateCount.lessThan=" + UPDATED_RATE_COUNT);
    }

    @Test
    @Transactional
    public void getAllSkillsByTeamsIsEqualToSomething() throws Exception {
        // Initialize the database
        TeamSkill teams = TeamSkillResourceIntTest.createEntity(em);
        em.persist(teams);
        em.flush();
        skill.addTeams(teams);
        skillRepository.saveAndFlush(skill);
        Long teamsId = teams.getId();

        // Get all the skillList where teams equals to teamsId
        defaultSkillShouldBeFound("teamsId.equals=" + teamsId);

        // Get all the skillList where teams equals to teamsId + 1
        defaultSkillShouldNotBeFound("teamsId.equals=" + (teamsId + 1));
    }


    @Test
    @Transactional
    public void getAllSkillsByBadgesIsEqualToSomething() throws Exception {
        // Initialize the database
        BadgeSkill badges = BadgeSkillResourceIntTest.createEntity(em);
        em.persist(badges);
        em.flush();
        skill.addBadges(badges);
        skillRepository.saveAndFlush(skill);
        Long badgesId = badges.getId();

        // Get all the skillList where badges equals to badgesId
        defaultSkillShouldBeFound("badgesId.equals=" + badgesId);

        // Get all the skillList where badges equals to badgesId + 1
        defaultSkillShouldNotBeFound("badgesId.equals=" + (badgesId + 1));
    }


    @Test
    @Transactional
    public void getAllSkillsByLevelsIsEqualToSomething() throws Exception {
        // Initialize the database
        LevelSkill levels = LevelSkillResourceIntTest.createEntity(em);
        em.persist(levels);
        em.flush();
        skill.addLevels(levels);
        skillRepository.saveAndFlush(skill);
        Long levelsId = levels.getId();

        // Get all the skillList where levels equals to levelsId
        defaultSkillShouldBeFound("levelsId.equals=" + levelsId);

        // Get all the skillList where levels equals to levelsId + 1
        defaultSkillShouldNotBeFound("levelsId.equals=" + (levelsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSkillShouldBeFound(String filter) throws Exception {
        restSkillMockMvc.perform(get("/api/skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skill.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].implementation").value(hasItem(DEFAULT_IMPLEMENTATION.toString())))
            .andExpect(jsonPath("$.[*].validation").value(hasItem(DEFAULT_VALIDATION.toString())))
            .andExpect(jsonPath("$.[*].expiryPeriod").value(hasItem(DEFAULT_EXPIRY_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].rateScore").value(hasItem(DEFAULT_RATE_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].rateCount").value(hasItem(DEFAULT_RATE_COUNT)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSkillShouldNotBeFound(String filter) throws Exception {
        restSkillMockMvc.perform(get("/api/skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingSkill() throws Exception {
        // Get the skill
        restSkillMockMvc.perform(get("/api/skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        int databaseSizeBeforeUpdate = skillRepository.findAll().size();

        // Update the skill
        Skill updatedSkill = skillRepository.findById(skill.getId()).get();
        // Disconnect from session so that the updates on updatedSkill are not directly saved in db
        em.detach(updatedSkill);
        updatedSkill
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .implementation(UPDATED_IMPLEMENTATION)
            .validation(UPDATED_VALIDATION)
            .expiryPeriod(UPDATED_EXPIRY_PERIOD)
            .contact(UPDATED_CONTACT)
            .score(UPDATED_SCORE)
            .expiryPeriod(UPDATED_EXPIRY_PERIOD).contact(UPDATED_CONTACT).rateScore(UPDATED_RATE_SCORE)
            .rateCount(UPDATED_RATE_COUNT);
        SkillDTO skillDTO = skillMapper.toDto(updatedSkill);

        restSkillMockMvc.perform(put("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isOk());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
        Skill testSkill = skillList.get(skillList.size() - 1);
        assertThat(testSkill.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSkill.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSkill.getImplementation()).isEqualTo(UPDATED_IMPLEMENTATION);
        assertThat(testSkill.getValidation()).isEqualTo(UPDATED_VALIDATION);
        assertThat(testSkill.getExpiryPeriod()).isEqualTo(UPDATED_EXPIRY_PERIOD);
        assertThat(testSkill.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testSkill.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testSkill.getRateScore()).isEqualTo(UPDATED_RATE_SCORE);
        assertThat(testSkill.getRateCount()).isEqualTo(UPDATED_RATE_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();

        // Create the Skill
        SkillDTO skillDTO = skillMapper.toDto(skill);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSkillMockMvc.perform(put("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillDTO)))
            .andExpect(status().isCreated());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        int databaseSizeBeforeDelete = skillRepository.findAll().size();

        // Get the skill
        restSkillMockMvc.perform(delete("/api/skills/{id}", skill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Skill.class);
        Skill skill1 = new Skill();
        skill1.setId(1L);
        Skill skill2 = new Skill();
        skill2.setId(skill1.getId());
        assertThat(skill1).isEqualTo(skill2);
        skill2.setId(2L);
        assertThat(skill1).isNotEqualTo(skill2);
        skill1.setId(null);
        assertThat(skill1).isNotEqualTo(skill2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillDTO.class);
        SkillDTO skillDTO1 = new SkillDTO();
        skillDTO1.setId(1L);
        SkillDTO skillDTO2 = new SkillDTO();
        assertThat(skillDTO1).isNotEqualTo(skillDTO2);
        skillDTO2.setId(skillDTO1.getId());
        assertThat(skillDTO1).isEqualTo(skillDTO2);
        skillDTO2.setId(2L);
        assertThat(skillDTO1).isNotEqualTo(skillDTO2);
        skillDTO1.setId(null);
        assertThat(skillDTO1).isNotEqualTo(skillDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(skillMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(skillMapper.fromId(null)).isNull();
    }
}
