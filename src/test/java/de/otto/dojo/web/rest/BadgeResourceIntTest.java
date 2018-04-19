package de.otto.dojo.web.rest;

import de.otto.dojo.DojoApp;

import de.otto.dojo.domain.Badge;
import de.otto.dojo.domain.BadgeSkill;
import de.otto.dojo.repository.BadgeRepository;
import de.otto.dojo.service.BadgeService;
import de.otto.dojo.web.rest.errors.ExceptionTranslator;
import de.otto.dojo.service.dto.BadgeCriteria;
import de.otto.dojo.service.BadgeQueryService;

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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ArrayList;

import static de.otto.dojo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BadgeResource REST controller.
 *
 * @see BadgeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DojoApp.class)
public class BadgeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_AVAILABLE_UNTIL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_AVAILABLE_UNTIL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_AVAILABLE_AMOUNT = 1;
    private static final Integer UPDATED_AVAILABLE_AMOUNT = 2;

    private static final Double DEFAULT_REQUIRED_SCORE = 0D;
    private static final Double UPDATED_REQUIRED_SCORE = 1D;

    @Autowired
    private BadgeRepository badgeRepository;


    

    @Autowired
    private BadgeService badgeService;

    @Autowired
    private BadgeQueryService badgeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBadgeMockMvc;

    private Badge badge;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BadgeResource badgeResource = new BadgeResource(badgeService, badgeQueryService);
        this.restBadgeMockMvc = MockMvcBuilders.standaloneSetup(badgeResource)
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
    public static Badge createEntity(EntityManager em) {
        Badge badge = new Badge()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .availableUntil(DEFAULT_AVAILABLE_UNTIL)
            .availableAmount(DEFAULT_AVAILABLE_AMOUNT)
            .requiredScore(DEFAULT_REQUIRED_SCORE);
        return badge;
    }

    @Before
    public void initTest() {
        badge = createEntity(em);
    }

    @Test
    @Transactional
    public void createBadge() throws Exception {
        int databaseSizeBeforeCreate = badgeRepository.findAll().size();

        // Create the Badge
        restBadgeMockMvc.perform(post("/api/badges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(badge)))
            .andExpect(status().isCreated());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeCreate + 1);
        Badge testBadge = badgeList.get(badgeList.size() - 1);
        assertThat(testBadge.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBadge.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBadge.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testBadge.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testBadge.getAvailableUntil()).isEqualTo(DEFAULT_AVAILABLE_UNTIL);
        assertThat(testBadge.getAvailableAmount()).isEqualTo(DEFAULT_AVAILABLE_AMOUNT);
        assertThat(testBadge.getRequiredScore()).isEqualTo(DEFAULT_REQUIRED_SCORE);
    }

    @Test
    @Transactional
    public void createBadgeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = badgeRepository.findAll().size();

        // Create the Badge with an existing ID
        badge.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBadgeMockMvc.perform(post("/api/badges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(badge)))
            .andExpect(status().isBadRequest());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = badgeRepository.findAll().size();
        // set the field null
        badge.setName(null);

        // Create the Badge, which fails.

        restBadgeMockMvc.perform(post("/api/badges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(badge)))
            .andExpect(status().isBadRequest());

        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBadges() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList
        restBadgeMockMvc.perform(get("/api/badges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(badge.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].availableUntil").value(hasItem(DEFAULT_AVAILABLE_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].availableAmount").value(hasItem(DEFAULT_AVAILABLE_AMOUNT)))
            .andExpect(jsonPath("$.[*].requiredScore").value(hasItem(DEFAULT_REQUIRED_SCORE.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getBadge() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get the badge
        restBadgeMockMvc.perform(get("/api/badges/{id}", badge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(badge.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.availableUntil").value(DEFAULT_AVAILABLE_UNTIL.toString()))
            .andExpect(jsonPath("$.availableAmount").value(DEFAULT_AVAILABLE_AMOUNT))
            .andExpect(jsonPath("$.requiredScore").value(DEFAULT_REQUIRED_SCORE.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllBadgesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where name equals to DEFAULT_NAME
        defaultBadgeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the badgeList where name equals to UPDATED_NAME
        defaultBadgeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBadgesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBadgeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the badgeList where name equals to UPDATED_NAME
        defaultBadgeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBadgesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where name is not null
        defaultBadgeShouldBeFound("name.specified=true");

        // Get all the badgeList where name is null
        defaultBadgeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllBadgesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where description equals to DEFAULT_DESCRIPTION
        defaultBadgeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the badgeList where description equals to UPDATED_DESCRIPTION
        defaultBadgeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBadgesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultBadgeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the badgeList where description equals to UPDATED_DESCRIPTION
        defaultBadgeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBadgesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where description is not null
        defaultBadgeShouldBeFound("description.specified=true");

        // Get all the badgeList where description is null
        defaultBadgeShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllBadgesByAvailableUntilIsEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableUntil equals to DEFAULT_AVAILABLE_UNTIL
        defaultBadgeShouldBeFound("availableUntil.equals=" + DEFAULT_AVAILABLE_UNTIL);

        // Get all the badgeList where availableUntil equals to UPDATED_AVAILABLE_UNTIL
        defaultBadgeShouldNotBeFound("availableUntil.equals=" + UPDATED_AVAILABLE_UNTIL);
    }

    @Test
    @Transactional
    public void getAllBadgesByAvailableUntilIsInShouldWork() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableUntil in DEFAULT_AVAILABLE_UNTIL or UPDATED_AVAILABLE_UNTIL
        defaultBadgeShouldBeFound("availableUntil.in=" + DEFAULT_AVAILABLE_UNTIL + "," + UPDATED_AVAILABLE_UNTIL);

        // Get all the badgeList where availableUntil equals to UPDATED_AVAILABLE_UNTIL
        defaultBadgeShouldNotBeFound("availableUntil.in=" + UPDATED_AVAILABLE_UNTIL);
    }

    @Test
    @Transactional
    public void getAllBadgesByAvailableUntilIsNullOrNotNull() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableUntil is not null
        defaultBadgeShouldBeFound("availableUntil.specified=true");

        // Get all the badgeList where availableUntil is null
        defaultBadgeShouldNotBeFound("availableUntil.specified=false");
    }

    @Test
    @Transactional
    public void getAllBadgesByAvailableAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableAmount equals to DEFAULT_AVAILABLE_AMOUNT
        defaultBadgeShouldBeFound("availableAmount.equals=" + DEFAULT_AVAILABLE_AMOUNT);

        // Get all the badgeList where availableAmount equals to UPDATED_AVAILABLE_AMOUNT
        defaultBadgeShouldNotBeFound("availableAmount.equals=" + UPDATED_AVAILABLE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBadgesByAvailableAmountIsInShouldWork() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableAmount in DEFAULT_AVAILABLE_AMOUNT or UPDATED_AVAILABLE_AMOUNT
        defaultBadgeShouldBeFound("availableAmount.in=" + DEFAULT_AVAILABLE_AMOUNT + "," + UPDATED_AVAILABLE_AMOUNT);

        // Get all the badgeList where availableAmount equals to UPDATED_AVAILABLE_AMOUNT
        defaultBadgeShouldNotBeFound("availableAmount.in=" + UPDATED_AVAILABLE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBadgesByAvailableAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableAmount is not null
        defaultBadgeShouldBeFound("availableAmount.specified=true");

        // Get all the badgeList where availableAmount is null
        defaultBadgeShouldNotBeFound("availableAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllBadgesByAvailableAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableAmount greater than or equals to DEFAULT_AVAILABLE_AMOUNT
        defaultBadgeShouldBeFound("availableAmount.greaterOrEqualThan=" + DEFAULT_AVAILABLE_AMOUNT);

        // Get all the badgeList where availableAmount greater than or equals to UPDATED_AVAILABLE_AMOUNT
        defaultBadgeShouldNotBeFound("availableAmount.greaterOrEqualThan=" + UPDATED_AVAILABLE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBadgesByAvailableAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where availableAmount less than or equals to DEFAULT_AVAILABLE_AMOUNT
        defaultBadgeShouldNotBeFound("availableAmount.lessThan=" + DEFAULT_AVAILABLE_AMOUNT);

        // Get all the badgeList where availableAmount less than or equals to UPDATED_AVAILABLE_AMOUNT
        defaultBadgeShouldBeFound("availableAmount.lessThan=" + UPDATED_AVAILABLE_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllBadgesByRequiredScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where requiredScore equals to DEFAULT_REQUIRED_SCORE
        defaultBadgeShouldBeFound("requiredScore.equals=" + DEFAULT_REQUIRED_SCORE);

        // Get all the badgeList where requiredScore equals to UPDATED_REQUIRED_SCORE
        defaultBadgeShouldNotBeFound("requiredScore.equals=" + UPDATED_REQUIRED_SCORE);
    }

    @Test
    @Transactional
    public void getAllBadgesByRequiredScoreIsInShouldWork() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where requiredScore in DEFAULT_REQUIRED_SCORE or UPDATED_REQUIRED_SCORE
        defaultBadgeShouldBeFound("requiredScore.in=" + DEFAULT_REQUIRED_SCORE + "," + UPDATED_REQUIRED_SCORE);

        // Get all the badgeList where requiredScore equals to UPDATED_REQUIRED_SCORE
        defaultBadgeShouldNotBeFound("requiredScore.in=" + UPDATED_REQUIRED_SCORE);
    }

    @Test
    @Transactional
    public void getAllBadgesByRequiredScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList where requiredScore is not null
        defaultBadgeShouldBeFound("requiredScore.specified=true");

        // Get all the badgeList where requiredScore is null
        defaultBadgeShouldNotBeFound("requiredScore.specified=false");
    }

    @Test
    @Transactional
    public void getAllBadgesBySkillsIsEqualToSomething() throws Exception {
        // Initialize the database
        BadgeSkill skills = BadgeSkillResourceIntTest.createEntity(em);
        em.persist(skills);
        em.flush();
        badge.addSkills(skills);
        badgeRepository.saveAndFlush(badge);
        Long skillsId = skills.getId();

        // Get all the badgeList where skills equals to skillsId
        defaultBadgeShouldBeFound("skillsId.equals=" + skillsId);

        // Get all the badgeList where skills equals to skillsId + 1
        defaultBadgeShouldNotBeFound("skillsId.equals=" + (skillsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBadgeShouldBeFound(String filter) throws Exception {
        restBadgeMockMvc.perform(get("/api/badges?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(badge.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].availableUntil").value(hasItem(DEFAULT_AVAILABLE_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].availableAmount").value(hasItem(DEFAULT_AVAILABLE_AMOUNT)))
            .andExpect(jsonPath("$.[*].requiredScore").value(hasItem(DEFAULT_REQUIRED_SCORE.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBadgeShouldNotBeFound(String filter) throws Exception {
        restBadgeMockMvc.perform(get("/api/badges?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingBadge() throws Exception {
        // Get the badge
        restBadgeMockMvc.perform(get("/api/badges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBadge() throws Exception {
        // Initialize the database
        badgeService.save(badge);

        int databaseSizeBeforeUpdate = badgeRepository.findAll().size();

        // Update the badge
        Badge updatedBadge = badgeRepository.findById(badge.getId()).get();
        // Disconnect from session so that the updates on updatedBadge are not directly saved in db
        em.detach(updatedBadge);
        updatedBadge
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .availableUntil(UPDATED_AVAILABLE_UNTIL)
            .availableAmount(UPDATED_AVAILABLE_AMOUNT)
            .requiredScore(UPDATED_REQUIRED_SCORE);

        restBadgeMockMvc.perform(put("/api/badges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBadge)))
            .andExpect(status().isOk());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeUpdate);
        Badge testBadge = badgeList.get(badgeList.size() - 1);
        assertThat(testBadge.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBadge.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBadge.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testBadge.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testBadge.getAvailableUntil()).isEqualTo(UPDATED_AVAILABLE_UNTIL);
        assertThat(testBadge.getAvailableAmount()).isEqualTo(UPDATED_AVAILABLE_AMOUNT);
        assertThat(testBadge.getRequiredScore()).isEqualTo(UPDATED_REQUIRED_SCORE);
    }

    @Test
    @Transactional
    public void updateNonExistingBadge() throws Exception {
        int databaseSizeBeforeUpdate = badgeRepository.findAll().size();

        // Create the Badge

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBadgeMockMvc.perform(put("/api/badges")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(badge)))
            .andExpect(status().isCreated());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBadge() throws Exception {
        // Initialize the database
        badgeService.save(badge);

        int databaseSizeBeforeDelete = badgeRepository.findAll().size();

        // Get the badge
        restBadgeMockMvc.perform(delete("/api/badges/{id}", badge.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Badge.class);
        Badge badge1 = new Badge();
        badge1.setId(1L);
        Badge badge2 = new Badge();
        badge2.setId(badge1.getId());
        assertThat(badge1).isEqualTo(badge2);
        badge2.setId(2L);
        assertThat(badge1).isNotEqualTo(badge2);
        badge1.setId(null);
        assertThat(badge1).isNotEqualTo(badge2);
    }
}
