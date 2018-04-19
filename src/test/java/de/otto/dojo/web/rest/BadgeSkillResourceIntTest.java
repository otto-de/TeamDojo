package de.otto.dojo.web.rest;

import de.otto.dojo.DojoApp;

import de.otto.dojo.domain.BadgeSkill;
import de.otto.dojo.domain.Badge;
import de.otto.dojo.repository.BadgeSkillRepository;
import de.otto.dojo.service.BadgeSkillService;
import de.otto.dojo.web.rest.errors.ExceptionTranslator;
import de.otto.dojo.service.dto.BadgeSkillCriteria;
import de.otto.dojo.service.BadgeSkillQueryService;

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
import java.util.List;
import java.util.ArrayList;

import static de.otto.dojo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BadgeSkillResource REST controller.
 *
 * @see BadgeSkillResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DojoApp.class)
public class BadgeSkillResourceIntTest {

    private static final Integer DEFAULT_SCORE = 0;
    private static final Integer UPDATED_SCORE = 1;

    @Autowired
    private BadgeSkillRepository badgeSkillRepository;


    

    @Autowired
    private BadgeSkillService badgeSkillService;

    @Autowired
    private BadgeSkillQueryService badgeSkillQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBadgeSkillMockMvc;

    private BadgeSkill badgeSkill;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BadgeSkillResource badgeSkillResource = new BadgeSkillResource(badgeSkillService, badgeSkillQueryService);
        this.restBadgeSkillMockMvc = MockMvcBuilders.standaloneSetup(badgeSkillResource)
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
    public static BadgeSkill createEntity(EntityManager em) {
        BadgeSkill badgeSkill = new BadgeSkill()
            .score(DEFAULT_SCORE);
        // Add required entity
        Badge badge = BadgeResourceIntTest.createEntity(em);
        em.persist(badge);
        em.flush();
        badgeSkill.setBadge(badge);
        return badgeSkill;
    }

    @Before
    public void initTest() {
        badgeSkill = createEntity(em);
    }

    @Test
    @Transactional
    public void createBadgeSkill() throws Exception {
        int databaseSizeBeforeCreate = badgeSkillRepository.findAll().size();

        // Create the BadgeSkill
        restBadgeSkillMockMvc.perform(post("/api/badge-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(badgeSkill)))
            .andExpect(status().isCreated());

        // Validate the BadgeSkill in the database
        List<BadgeSkill> badgeSkillList = badgeSkillRepository.findAll();
        assertThat(badgeSkillList).hasSize(databaseSizeBeforeCreate + 1);
        BadgeSkill testBadgeSkill = badgeSkillList.get(badgeSkillList.size() - 1);
        assertThat(testBadgeSkill.getScore()).isEqualTo(DEFAULT_SCORE);
    }

    @Test
    @Transactional
    public void createBadgeSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = badgeSkillRepository.findAll().size();

        // Create the BadgeSkill with an existing ID
        badgeSkill.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBadgeSkillMockMvc.perform(post("/api/badge-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(badgeSkill)))
            .andExpect(status().isBadRequest());

        // Validate the BadgeSkill in the database
        List<BadgeSkill> badgeSkillList = badgeSkillRepository.findAll();
        assertThat(badgeSkillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgeSkillRepository.findAll().size();
        // set the field null
        badgeSkill.setScore(null);

        // Create the BadgeSkill, which fails.

        restBadgeSkillMockMvc.perform(post("/api/badge-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(badgeSkill)))
            .andExpect(status().isBadRequest());

        List<BadgeSkill> badgeSkillList = badgeSkillRepository.findAll();
        assertThat(badgeSkillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBadgeSkills() throws Exception {
        // Initialize the database
        badgeSkillRepository.saveAndFlush(badgeSkill);

        // Get all the badgeSkillList
        restBadgeSkillMockMvc.perform(get("/api/badge-skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(badgeSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)));
    }
    

    @Test
    @Transactional
    public void getBadgeSkill() throws Exception {
        // Initialize the database
        badgeSkillRepository.saveAndFlush(badgeSkill);

        // Get the badgeSkill
        restBadgeSkillMockMvc.perform(get("/api/badge-skills/{id}", badgeSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(badgeSkill.getId().intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE));
    }

    @Test
    @Transactional
    public void getAllBadgeSkillsByScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        badgeSkillRepository.saveAndFlush(badgeSkill);

        // Get all the badgeSkillList where score equals to DEFAULT_SCORE
        defaultBadgeSkillShouldBeFound("score.equals=" + DEFAULT_SCORE);

        // Get all the badgeSkillList where score equals to UPDATED_SCORE
        defaultBadgeSkillShouldNotBeFound("score.equals=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllBadgeSkillsByScoreIsInShouldWork() throws Exception {
        // Initialize the database
        badgeSkillRepository.saveAndFlush(badgeSkill);

        // Get all the badgeSkillList where score in DEFAULT_SCORE or UPDATED_SCORE
        defaultBadgeSkillShouldBeFound("score.in=" + DEFAULT_SCORE + "," + UPDATED_SCORE);

        // Get all the badgeSkillList where score equals to UPDATED_SCORE
        defaultBadgeSkillShouldNotBeFound("score.in=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllBadgeSkillsByScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        badgeSkillRepository.saveAndFlush(badgeSkill);

        // Get all the badgeSkillList where score is not null
        defaultBadgeSkillShouldBeFound("score.specified=true");

        // Get all the badgeSkillList where score is null
        defaultBadgeSkillShouldNotBeFound("score.specified=false");
    }

    @Test
    @Transactional
    public void getAllBadgeSkillsByScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        badgeSkillRepository.saveAndFlush(badgeSkill);

        // Get all the badgeSkillList where score greater than or equals to DEFAULT_SCORE
        defaultBadgeSkillShouldBeFound("score.greaterOrEqualThan=" + DEFAULT_SCORE);

        // Get all the badgeSkillList where score greater than or equals to UPDATED_SCORE
        defaultBadgeSkillShouldNotBeFound("score.greaterOrEqualThan=" + UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void getAllBadgeSkillsByScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        badgeSkillRepository.saveAndFlush(badgeSkill);

        // Get all the badgeSkillList where score less than or equals to DEFAULT_SCORE
        defaultBadgeSkillShouldNotBeFound("score.lessThan=" + DEFAULT_SCORE);

        // Get all the badgeSkillList where score less than or equals to UPDATED_SCORE
        defaultBadgeSkillShouldBeFound("score.lessThan=" + UPDATED_SCORE);
    }


    @Test
    @Transactional
    public void getAllBadgeSkillsByBadgeIsEqualToSomething() throws Exception {
        // Initialize the database
        Badge badge = BadgeResourceIntTest.createEntity(em);
        em.persist(badge);
        em.flush();
        badgeSkill.setBadge(badge);
        badgeSkillRepository.saveAndFlush(badgeSkill);
        Long badgeId = badge.getId();

        // Get all the badgeSkillList where badge equals to badgeId
        defaultBadgeSkillShouldBeFound("badgeId.equals=" + badgeId);

        // Get all the badgeSkillList where badge equals to badgeId + 1
        defaultBadgeSkillShouldNotBeFound("badgeId.equals=" + (badgeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBadgeSkillShouldBeFound(String filter) throws Exception {
        restBadgeSkillMockMvc.perform(get("/api/badge-skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(badgeSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBadgeSkillShouldNotBeFound(String filter) throws Exception {
        restBadgeSkillMockMvc.perform(get("/api/badge-skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingBadgeSkill() throws Exception {
        // Get the badgeSkill
        restBadgeSkillMockMvc.perform(get("/api/badge-skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBadgeSkill() throws Exception {
        // Initialize the database
        badgeSkillService.save(badgeSkill);

        int databaseSizeBeforeUpdate = badgeSkillRepository.findAll().size();

        // Update the badgeSkill
        BadgeSkill updatedBadgeSkill = badgeSkillRepository.findById(badgeSkill.getId()).get();
        // Disconnect from session so that the updates on updatedBadgeSkill are not directly saved in db
        em.detach(updatedBadgeSkill);
        updatedBadgeSkill
            .score(UPDATED_SCORE);

        restBadgeSkillMockMvc.perform(put("/api/badge-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBadgeSkill)))
            .andExpect(status().isOk());

        // Validate the BadgeSkill in the database
        List<BadgeSkill> badgeSkillList = badgeSkillRepository.findAll();
        assertThat(badgeSkillList).hasSize(databaseSizeBeforeUpdate);
        BadgeSkill testBadgeSkill = badgeSkillList.get(badgeSkillList.size() - 1);
        assertThat(testBadgeSkill.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    @Transactional
    public void updateNonExistingBadgeSkill() throws Exception {
        int databaseSizeBeforeUpdate = badgeSkillRepository.findAll().size();

        // Create the BadgeSkill

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBadgeSkillMockMvc.perform(put("/api/badge-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(badgeSkill)))
            .andExpect(status().isCreated());

        // Validate the BadgeSkill in the database
        List<BadgeSkill> badgeSkillList = badgeSkillRepository.findAll();
        assertThat(badgeSkillList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBadgeSkill() throws Exception {
        // Initialize the database
        badgeSkillService.save(badgeSkill);

        int databaseSizeBeforeDelete = badgeSkillRepository.findAll().size();

        // Get the badgeSkill
        restBadgeSkillMockMvc.perform(delete("/api/badge-skills/{id}", badgeSkill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BadgeSkill> badgeSkillList = badgeSkillRepository.findAll();
        assertThat(badgeSkillList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BadgeSkill.class);
        BadgeSkill badgeSkill1 = new BadgeSkill();
        badgeSkill1.setId(1L);
        BadgeSkill badgeSkill2 = new BadgeSkill();
        badgeSkill2.setId(badgeSkill1.getId());
        assertThat(badgeSkill1).isEqualTo(badgeSkill2);
        badgeSkill2.setId(2L);
        assertThat(badgeSkill1).isNotEqualTo(badgeSkill2);
        badgeSkill1.setId(null);
        assertThat(badgeSkill1).isNotEqualTo(badgeSkill2);
    }
}
