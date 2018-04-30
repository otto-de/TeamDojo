package de.otto.teamdojo.web.rest;

import de.otto.teamdojo.TeamdojoApp;
import de.otto.teamdojo.domain.Badge;
import de.otto.teamdojo.domain.Dimension;
import de.otto.teamdojo.domain.Level;
import de.otto.teamdojo.domain.Team;
import de.otto.teamdojo.repository.DimensionRepository;
import de.otto.teamdojo.service.DimensionQueryService;
import de.otto.teamdojo.service.DimensionService;
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
 * Test class for the DimensionResource REST controller.
 *
 * @see DimensionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TeamdojoApp.class)
public class DimensionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private DimensionRepository dimensionRepository;


    @Autowired
    private DimensionService dimensionService;

    @Autowired
    private DimensionQueryService dimensionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDimensionMockMvc;

    private Dimension dimension;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DimensionResource dimensionResource = new DimensionResource(dimensionService, dimensionQueryService);
        this.restDimensionMockMvc = MockMvcBuilders.standaloneSetup(dimensionResource)
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
    public static Dimension createEntity(EntityManager em) {
        Dimension dimension = new Dimension()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return dimension;
    }

    @Before
    public void initTest() {
        dimension = createEntity(em);
    }

    @Test
    @Transactional
    public void createDimension() throws Exception {
        int databaseSizeBeforeCreate = dimensionRepository.findAll().size();

        // Create the Dimension
        restDimensionMockMvc.perform(post("/api/dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimension)))
            .andExpect(status().isCreated());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeCreate + 1);
        Dimension testDimension = dimensionList.get(dimensionList.size() - 1);
        assertThat(testDimension.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDimension.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createDimensionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dimensionRepository.findAll().size();

        // Create the Dimension with an existing ID
        dimension.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDimensionMockMvc.perform(post("/api/dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimension)))
            .andExpect(status().isBadRequest());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dimensionRepository.findAll().size();
        // set the field null
        dimension.setName(null);

        // Create the Dimension, which fails.

        restDimensionMockMvc.perform(post("/api/dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimension)))
            .andExpect(status().isBadRequest());

        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDimensions() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList
        restDimensionMockMvc.perform(get("/api/dimensions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dimension.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }


    @Test
    @Transactional
    public void getDimension() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get the dimension
        restDimensionMockMvc.perform(get("/api/dimensions/{id}", dimension.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dimension.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllDimensionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where name equals to DEFAULT_NAME
        defaultDimensionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the dimensionList where name equals to UPDATED_NAME
        defaultDimensionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDimensionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDimensionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the dimensionList where name equals to UPDATED_NAME
        defaultDimensionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDimensionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where name is not null
        defaultDimensionShouldBeFound("name.specified=true");

        // Get all the dimensionList where name is null
        defaultDimensionShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllDimensionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where description equals to DEFAULT_DESCRIPTION
        defaultDimensionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the dimensionList where description equals to UPDATED_DESCRIPTION
        defaultDimensionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDimensionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultDimensionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the dimensionList where description equals to UPDATED_DESCRIPTION
        defaultDimensionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDimensionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList where description is not null
        defaultDimensionShouldBeFound("description.specified=true");

        // Get all the dimensionList where description is null
        defaultDimensionShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllDimensionsByParticipantsIsEqualToSomething() throws Exception {
        // Initialize the database
        Team participants = TeamResourceIntTest.createEntity(em);
        em.persist(participants);
        em.flush();
        dimension.addParticipants(participants);
        dimensionRepository.saveAndFlush(dimension);
        Long participantsId = participants.getId();

        // Get all the dimensionList where participants equals to participantsId
        defaultDimensionShouldBeFound("participantsId.equals=" + participantsId);

        // Get all the dimensionList where participants equals to participantsId + 1
        defaultDimensionShouldNotBeFound("participantsId.equals=" + (participantsId + 1));
    }


    @Test
    @Transactional
    public void getAllDimensionsByLevelsIsEqualToSomething() throws Exception {
        // Initialize the database
        Level levels = LevelResourceIntTest.createEntity(em);
        em.persist(levels);
        em.flush();
        dimension.addLevels(levels);
        dimensionRepository.saveAndFlush(dimension);
        Long levelsId = levels.getId();

        // Get all the dimensionList where levels equals to levelsId
        defaultDimensionShouldBeFound("levelsId.equals=" + levelsId);

        // Get all the dimensionList where levels equals to levelsId + 1
        defaultDimensionShouldNotBeFound("levelsId.equals=" + (levelsId + 1));
    }


    @Test
    @Transactional
    public void getAllDimensionsByBadgesIsEqualToSomething() throws Exception {
        // Initialize the database
        Badge badges = BadgeResourceIntTest.createEntity(em);
        em.persist(badges);
        em.flush();
        dimension.addBadges(badges);
        dimensionRepository.saveAndFlush(dimension);
        Long badgesId = badges.getId();

        // Get all the dimensionList where badges equals to badgesId
        defaultDimensionShouldBeFound("badgesId.equals=" + badgesId);

        // Get all the dimensionList where badges equals to badgesId + 1
        defaultDimensionShouldNotBeFound("badgesId.equals=" + (badgesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDimensionShouldBeFound(String filter) throws Exception {
        restDimensionMockMvc.perform(get("/api/dimensions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dimension.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDimensionShouldNotBeFound(String filter) throws Exception {
        restDimensionMockMvc.perform(get("/api/dimensions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingDimension() throws Exception {
        // Get the dimension
        restDimensionMockMvc.perform(get("/api/dimensions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDimension() throws Exception {
        // Initialize the database
        dimensionService.save(dimension);

        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();

        // Update the dimension
        Dimension updatedDimension = dimensionRepository.findById(dimension.getId()).get();
        // Disconnect from session so that the updates on updatedDimension are not directly saved in db
        em.detach(updatedDimension);
        updatedDimension
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restDimensionMockMvc.perform(put("/api/dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDimension)))
            .andExpect(status().isOk());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
        Dimension testDimension = dimensionList.get(dimensionList.size() - 1);
        assertThat(testDimension.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDimension.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingDimension() throws Exception {
        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();

        // Create the Dimension

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDimensionMockMvc.perform(put("/api/dimensions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dimension)))
            .andExpect(status().isCreated());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDimension() throws Exception {
        // Initialize the database
        dimensionService.save(dimension);

        int databaseSizeBeforeDelete = dimensionRepository.findAll().size();

        // Get the dimension
        restDimensionMockMvc.perform(delete("/api/dimensions/{id}", dimension.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dimension.class);
        Dimension dimension1 = new Dimension();
        dimension1.setId(1L);
        Dimension dimension2 = new Dimension();
        dimension2.setId(dimension1.getId());
        assertThat(dimension1).isEqualTo(dimension2);
        dimension2.setId(2L);
        assertThat(dimension1).isNotEqualTo(dimension2);
        dimension1.setId(null);
        assertThat(dimension1).isNotEqualTo(dimension2);
    }
}
