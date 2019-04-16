package de.otto.teamdojo.web.rest;

import de.otto.teamdojo.TeamdojoApp;
import de.otto.teamdojo.domain.Level;
import de.otto.teamdojo.domain.LevelSkill;
import de.otto.teamdojo.domain.Skill;
import de.otto.teamdojo.repository.LevelSkillRepository;
import de.otto.teamdojo.service.LevelSkillQueryService;
import de.otto.teamdojo.service.LevelSkillService;
import de.otto.teamdojo.service.dto.LevelSkillDTO;
import de.otto.teamdojo.service.mapper.LevelSkillMapper;
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
 * Test class for the LevelSkillResource REST controller.
 *
 * @see LevelSkillResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TeamdojoApp.class)
public class LevelSkillResourceIntTest {

    @Autowired
    private LevelSkillRepository levelSkillRepository;


    @Autowired
    private LevelSkillMapper levelSkillMapper;


    @Autowired
    private LevelSkillService levelSkillService;

    @Autowired
    private LevelSkillQueryService levelSkillQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLevelSkillMockMvc;

    private LevelSkill levelSkill;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelSkill createEntity(EntityManager em) {
        LevelSkill levelSkill = new LevelSkill();
        // Add required entity
        Skill skill = SkillResourceIntTest.createEntity(em);
        em.persist(skill);
        em.flush();
        levelSkill.setSkill(skill);
        // Add required entity
        Level level = LevelResourceIntTest.createEntity(em);
        em.persist(level);
        em.flush();
        levelSkill.setLevel(level);
        return levelSkill;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LevelSkillResource levelSkillResource = new LevelSkillResource(levelSkillService, levelSkillQueryService);
        this.restLevelSkillMockMvc = MockMvcBuilders.standaloneSetup(levelSkillResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        levelSkill = createEntity(em);
    }

    @Test
    @Transactional
    public void createLevelSkill() throws Exception {
        int databaseSizeBeforeCreate = levelSkillRepository.findAll().size();

        // Create the LevelSkill
        LevelSkillDTO levelSkillDTO = levelSkillMapper.toDto(levelSkill);
        restLevelSkillMockMvc.perform(post("/api/level-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(levelSkillDTO)))
            .andExpect(status().isCreated());

        // Validate the LevelSkill in the database
        List<LevelSkill> levelSkillList = levelSkillRepository.findAll();
        assertThat(levelSkillList).hasSize(databaseSizeBeforeCreate + 1);
        LevelSkill testLevelSkill = levelSkillList.get(levelSkillList.size() - 1);
    }

    @Test
    @Transactional
    public void createLevelSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = levelSkillRepository.findAll().size();

        // Create the LevelSkill with an existing ID
        levelSkill.setId(1L);
        LevelSkillDTO levelSkillDTO = levelSkillMapper.toDto(levelSkill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLevelSkillMockMvc.perform(post("/api/level-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(levelSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LevelSkill in the database
        List<LevelSkill> levelSkillList = levelSkillRepository.findAll();
        assertThat(levelSkillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLevelSkills() throws Exception {
        // Initialize the database
        levelSkillRepository.saveAndFlush(levelSkill);

        // Get all the levelSkillList
        restLevelSkillMockMvc.perform(get("/api/level-skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(levelSkill.getId().intValue())));
    }


    @Test
    @Transactional
    public void getLevelSkill() throws Exception {
        // Initialize the database
        levelSkillRepository.saveAndFlush(levelSkill);

        // Get the levelSkill
        restLevelSkillMockMvc.perform(get("/api/level-skills/{id}", levelSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(levelSkill.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllLevelSkillsBySkillIsEqualToSomething() throws Exception {
        // Initialize the database
        Skill skill = SkillResourceIntTest.createEntity(em);
        em.persist(skill);
        em.flush();
        levelSkill.setSkill(skill);
        levelSkillRepository.saveAndFlush(levelSkill);
        Long skillId = skill.getId();

        // Get all the levelSkillList where skill equals to skillId
        defaultLevelSkillShouldBeFound("skillId.equals=" + skillId);

        // Get all the levelSkillList where skill equals to skillId + 1
        defaultLevelSkillShouldNotBeFound("skillId.equals=" + (skillId + 1));
    }


    @Test
    @Transactional
    public void getAllLevelSkillsByLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        Level level = LevelResourceIntTest.createEntity(em);
        em.persist(level);
        em.flush();
        levelSkill.setLevel(level);
        levelSkillRepository.saveAndFlush(levelSkill);
        Long levelId = level.getId();

        // Get all the levelSkillList where level equals to levelId
        defaultLevelSkillShouldBeFound("levelId.equals=" + levelId);

        // Get all the levelSkillList where level equals to levelId + 1
        defaultLevelSkillShouldNotBeFound("levelId.equals=" + (levelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultLevelSkillShouldBeFound(String filter) throws Exception {
        restLevelSkillMockMvc.perform(get("/api/level-skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(levelSkill.getId().intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultLevelSkillShouldNotBeFound(String filter) throws Exception {
        restLevelSkillMockMvc.perform(get("/api/level-skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingLevelSkill() throws Exception {
        // Get the levelSkill
        restLevelSkillMockMvc.perform(get("/api/level-skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLevelSkill() throws Exception {
        // Initialize the database
        levelSkillRepository.saveAndFlush(levelSkill);

        int databaseSizeBeforeUpdate = levelSkillRepository.findAll().size();

        // Update the levelSkill
        LevelSkill updatedLevelSkill = levelSkillRepository.findById(levelSkill.getId()).get();
        // Disconnect from session so that the updates on updatedLevelSkill are not directly saved in db
        em.detach(updatedLevelSkill);
        LevelSkillDTO levelSkillDTO = levelSkillMapper.toDto(updatedLevelSkill);

        restLevelSkillMockMvc.perform(put("/api/level-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(levelSkillDTO)))
            .andExpect(status().isOk());

        // Validate the LevelSkill in the database
        List<LevelSkill> levelSkillList = levelSkillRepository.findAll();
        assertThat(levelSkillList).hasSize(databaseSizeBeforeUpdate);
        LevelSkill testLevelSkill = levelSkillList.get(levelSkillList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingLevelSkill() throws Exception {
        int databaseSizeBeforeUpdate = levelSkillRepository.findAll().size();

        // Create the LevelSkill
        LevelSkillDTO levelSkillDTO = levelSkillMapper.toDto(levelSkill);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLevelSkillMockMvc.perform(put("/api/level-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(levelSkillDTO)))
            .andExpect(status().isCreated());

        // Validate the LevelSkill in the database
        List<LevelSkill> levelSkillList = levelSkillRepository.findAll();
        assertThat(levelSkillList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLevelSkill() throws Exception {
        // Initialize the database
        levelSkillRepository.saveAndFlush(levelSkill);

        int databaseSizeBeforeDelete = levelSkillRepository.findAll().size();

        // Get the levelSkill
        restLevelSkillMockMvc.perform(delete("/api/level-skills/{id}", levelSkill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LevelSkill> levelSkillList = levelSkillRepository.findAll();
        assertThat(levelSkillList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LevelSkill.class);
        LevelSkill levelSkill1 = new LevelSkill();
        levelSkill1.setId(1L);
        LevelSkill levelSkill2 = new LevelSkill();
        levelSkill2.setId(levelSkill1.getId());
        assertThat(levelSkill1).isEqualTo(levelSkill2);
        levelSkill2.setId(2L);
        assertThat(levelSkill1).isNotEqualTo(levelSkill2);
        levelSkill1.setId(null);
        assertThat(levelSkill1).isNotEqualTo(levelSkill2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LevelSkillDTO.class);
        LevelSkillDTO levelSkillDTO1 = new LevelSkillDTO();
        levelSkillDTO1.setId(1L);
        LevelSkillDTO levelSkillDTO2 = new LevelSkillDTO();
        assertThat(levelSkillDTO1).isNotEqualTo(levelSkillDTO2);
        levelSkillDTO2.setId(levelSkillDTO1.getId());
        assertThat(levelSkillDTO1).isEqualTo(levelSkillDTO2);
        levelSkillDTO2.setId(2L);
        assertThat(levelSkillDTO1).isNotEqualTo(levelSkillDTO2);
        levelSkillDTO1.setId(null);
        assertThat(levelSkillDTO1).isNotEqualTo(levelSkillDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(levelSkillMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(levelSkillMapper.fromId(null)).isNull();
    }
}
