package de.otto.teamdojo.web.rest;

import de.otto.teamdojo.TeamdojoApp;
import de.otto.teamdojo.domain.Dimension;
import de.otto.teamdojo.domain.Level;
import de.otto.teamdojo.domain.LevelSkill;
import de.otto.teamdojo.repository.LevelRepository;
import de.otto.teamdojo.service.LevelQueryService;
import de.otto.teamdojo.service.LevelService;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static de.otto.teamdojo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LevelResource REST controller.
 *
 * @see LevelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TeamdojoApp.class)
public class LevelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_CONTENT_TYPE = "image/png";

    private static final Float DEFAULT_REQUIRED_SCORE = 0F;
    private static final Float UPDATED_REQUIRED_SCORE = 1F;

    @Autowired
    private LevelRepository levelRepository;


    @Autowired
    private LevelService levelService;

    @Autowired
    private LevelQueryService levelQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLevelMockMvc;

    private Level level;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LevelResource levelResource = new LevelResource(levelService, levelQueryService);
        this.restLevelMockMvc = MockMvcBuilders.standaloneSetup(levelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Level createEntity(EntityManager em) {
        Level level = new Level()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .picture(DEFAULT_PICTURE)
            .pictureContentType(DEFAULT_PICTURE_CONTENT_TYPE)
            .requiredScore(DEFAULT_REQUIRED_SCORE);
        // Add required entity
        Dimension dimension = DimensionResourceIntTest.createEntity(em);
        em.persist(dimension);
        em.flush();
        level.setDimension(dimension);
        return level;
    }

    @Before
    public void initTest() {
        level = createEntity(em);
    }

    @Test
    @Transactional
    public void createLevel() throws Exception {
        int databaseSizeBeforeCreate = levelRepository.findAll().size();

        // Create the Level
        restLevelMockMvc.perform(post("/api/levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(level)))
            .andExpect(status().isCreated());

        // Validate the Level in the database
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeCreate + 1);
        Level testLevel = levelList.get(levelList.size() - 1);
        assertThat(testLevel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLevel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLevel.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testLevel.getPictureContentType()).isEqualTo(DEFAULT_PICTURE_CONTENT_TYPE);
        assertThat(testLevel.getRequiredScore()).isEqualTo(DEFAULT_REQUIRED_SCORE);
    }

    @Test
    @Transactional
    public void createLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = levelRepository.findAll().size();

        // Create the Level with an existing ID
        level.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLevelMockMvc.perform(post("/api/levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(level)))
            .andExpect(status().isBadRequest());

        // Validate the Level in the database
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelRepository.findAll().size();
        // set the field null
        level.setName(null);

        // Create the Level, which fails.

        restLevelMockMvc.perform(post("/api/levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(level)))
            .andExpect(status().isBadRequest());

        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRequiredScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelRepository.findAll().size();
        // set the field null
        level.setRequiredScore(null);

        // Create the Level, which fails.

        restLevelMockMvc.perform(post("/api/levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(level)))
            .andExpect(status().isBadRequest());

        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLevels() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList
        restLevelMockMvc.perform(get("/api/levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(level.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE))))
            .andExpect(jsonPath("$.[*].requiredScore").value(hasItem(DEFAULT_REQUIRED_SCORE.doubleValue())));
    }


    @Test
    @Transactional
    public void getLevel() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get the level
        restLevelMockMvc.perform(get("/api/levels/{id}", level.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(level.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.pictureContentType").value(DEFAULT_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.picture").value(Base64Utils.encodeToString(DEFAULT_PICTURE)))
            .andExpect(jsonPath("$.requiredScore").value(DEFAULT_REQUIRED_SCORE.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllLevelsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where name equals to DEFAULT_NAME
        defaultLevelShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the levelList where name equals to UPDATED_NAME
        defaultLevelShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLevelsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where name in DEFAULT_NAME or UPDATED_NAME
        defaultLevelShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the levelList where name equals to UPDATED_NAME
        defaultLevelShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLevelsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where name is not null
        defaultLevelShouldBeFound("name.specified=true");

        // Get all the levelList where name is null
        defaultLevelShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllLevelsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where description equals to DEFAULT_DESCRIPTION
        defaultLevelShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the levelList where description equals to UPDATED_DESCRIPTION
        defaultLevelShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLevelsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultLevelShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the levelList where description equals to UPDATED_DESCRIPTION
        defaultLevelShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLevelsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where description is not null
        defaultLevelShouldBeFound("description.specified=true");

        // Get all the levelList where description is null
        defaultLevelShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllLevelsByRequiredScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where requiredScore equals to DEFAULT_REQUIRED_SCORE
        defaultLevelShouldBeFound("requiredScore.equals=" + DEFAULT_REQUIRED_SCORE);

        // Get all the levelList where requiredScore equals to UPDATED_REQUIRED_SCORE
        defaultLevelShouldNotBeFound("requiredScore.equals=" + UPDATED_REQUIRED_SCORE);
    }

    @Test
    @Transactional
    public void getAllLevelsByRequiredScoreIsInShouldWork() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where requiredScore in DEFAULT_REQUIRED_SCORE or UPDATED_REQUIRED_SCORE
        defaultLevelShouldBeFound("requiredScore.in=" + DEFAULT_REQUIRED_SCORE + "," + UPDATED_REQUIRED_SCORE);

        // Get all the levelList where requiredScore equals to UPDATED_REQUIRED_SCORE
        defaultLevelShouldNotBeFound("requiredScore.in=" + UPDATED_REQUIRED_SCORE);
    }

    @Test
    @Transactional
    public void getAllLevelsByRequiredScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        levelRepository.saveAndFlush(level);

        // Get all the levelList where requiredScore is not null
        defaultLevelShouldBeFound("requiredScore.specified=true");

        // Get all the levelList where requiredScore is null
        defaultLevelShouldNotBeFound("requiredScore.specified=false");
    }

    @Test
    @Transactional
    public void getAllLevelsByDimensionIsEqualToSomething() throws Exception {
        // Initialize the database
        Dimension dimension = DimensionResourceIntTest.createEntity(em);
        em.persist(dimension);
        em.flush();
        level.setDimension(dimension);
        levelRepository.saveAndFlush(level);
        Long dimensionId = dimension.getId();

        // Get all the levelList where dimension equals to dimensionId
        defaultLevelShouldBeFound("dimensionId.equals=" + dimensionId);

        // Get all the levelList where dimension equals to dimensionId + 1
        defaultLevelShouldNotBeFound("dimensionId.equals=" + (dimensionId + 1));
    }


    @Test
    @Transactional
    public void getAllLevelsByDependsOnIsEqualToSomething() throws Exception {
        // Initialize the database
        Level dependsOn = LevelResourceIntTest.createEntity(em);
        em.persist(dependsOn);
        em.flush();
        level.setDependsOn(dependsOn);
        levelRepository.saveAndFlush(level);
        Long dependsOnId = dependsOn.getId();

        // Get all the levelList where dependsOn equals to dependsOnId
        defaultLevelShouldBeFound("dependsOnId.equals=" + dependsOnId);

        // Get all the levelList where dependsOn equals to dependsOnId + 1
        defaultLevelShouldNotBeFound("dependsOnId.equals=" + (dependsOnId + 1));
    }


    @Test
    @Transactional
    public void getAllLevelsBySkillsIsEqualToSomething() throws Exception {
        // Initialize the database
        LevelSkill skills = LevelSkillResourceIntTest.createEntity(em);
        em.persist(skills);
        em.flush();
        level.addSkills(skills);
        levelRepository.saveAndFlush(level);
        Long skillsId = skills.getId();

        // Get all the levelList where skills equals to skillsId
        defaultLevelShouldBeFound("skillsId.equals=" + skillsId);

        // Get all the levelList where skills equals to skillsId + 1
        defaultLevelShouldNotBeFound("skillsId.equals=" + (skillsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultLevelShouldBeFound(String filter) throws Exception {
        restLevelMockMvc.perform(get("/api/levels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(level.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE))))
            .andExpect(jsonPath("$.[*].requiredScore").value(hasItem(DEFAULT_REQUIRED_SCORE.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultLevelShouldNotBeFound(String filter) throws Exception {
        restLevelMockMvc.perform(get("/api/levels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingLevel() throws Exception {
        // Get the level
        restLevelMockMvc.perform(get("/api/levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLevel() throws Exception {
        // Initialize the database
        levelService.save(level);

        int databaseSizeBeforeUpdate = levelRepository.findAll().size();

        // Update the level
        Level updatedLevel = levelRepository.findById(level.getId()).get();
        // Disconnect from session so that the updates on updatedLevel are not directly saved in db
        em.detach(updatedLevel);
        updatedLevel
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .requiredScore(UPDATED_REQUIRED_SCORE);

        restLevelMockMvc.perform(put("/api/levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLevel)))
            .andExpect(status().isOk());

        // Validate the Level in the database
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeUpdate);
        Level testLevel = levelList.get(levelList.size() - 1);
        assertThat(testLevel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLevel.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testLevel.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
        assertThat(testLevel.getRequiredScore()).isEqualTo(UPDATED_REQUIRED_SCORE);
    }

    @Test
    @Transactional
    public void updateNonExistingLevel() throws Exception {
        int databaseSizeBeforeUpdate = levelRepository.findAll().size();

        // Create the Level

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLevelMockMvc.perform(put("/api/levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(level)))
            .andExpect(status().isCreated());

        // Validate the Level in the database
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLevel() throws Exception {
        // Initialize the database
        levelService.save(level);

        int databaseSizeBeforeDelete = levelRepository.findAll().size();

        // Get the level
        restLevelMockMvc.perform(delete("/api/levels/{id}", level.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Level> levelList = levelRepository.findAll();
        assertThat(levelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Level.class);
        Level level1 = new Level();
        level1.setId(1L);
        Level level2 = new Level();
        level2.setId(level1.getId());
        assertThat(level1).isEqualTo(level2);
        level2.setId(2L);
        assertThat(level1).isNotEqualTo(level2);
        level1.setId(null);
        assertThat(level1).isNotEqualTo(level2);
    }
}
