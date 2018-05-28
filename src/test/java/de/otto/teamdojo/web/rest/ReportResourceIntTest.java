package de.otto.teamdojo.web.rest;

import de.otto.teamdojo.TeamdojoApp;

import de.otto.teamdojo.domain.Report;
import de.otto.teamdojo.repository.ReportRepository;
import de.otto.teamdojo.service.ReportService;
import de.otto.teamdojo.service.dto.ReportDTO;
import de.otto.teamdojo.service.mapper.ReportMapper;
import de.otto.teamdojo.web.rest.errors.ExceptionTranslator;
import de.otto.teamdojo.service.dto.ReportCriteria;
import de.otto.teamdojo.service.ReportQueryService;

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

import de.otto.teamdojo.domain.enumeration.ReportType;
/**
 * Test class for the ReportResource REST controller.
 *
 * @see ReportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TeamdojoApp.class)
public class ReportResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ReportType DEFAULT_TYPE = ReportType.BUG;
    private static final ReportType UPDATED_TYPE = ReportType.WISH;

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ReportRepository reportRepository;



    @Autowired
    private ReportMapper reportMapper;
    

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportQueryService reportQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReportMockMvc;

    private Report report;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReportResource reportResource = new ReportResource(reportService, reportQueryService);
        this.restReportMockMvc = MockMvcBuilders.standaloneSetup(reportResource)
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
    public static Report createEntity(EntityManager em) {
        Report report = new Report()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .creationDate(DEFAULT_CREATION_DATE);
        return report;
    }

    @Before
    public void initTest() {
        report = createEntity(em);
    }

    @Test
    @Transactional
    public void createReport() throws Exception {
        int databaseSizeBeforeCreate = reportRepository.findAll().size();

        // Create the Report
        ReportDTO reportDTO = reportMapper.toDto(report);
        restReportMockMvc.perform(post("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isCreated());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeCreate + 1);
        Report testReport = reportList.get(reportList.size() - 1);
        assertThat(testReport.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testReport.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testReport.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testReport.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
    }

    @Test
    @Transactional
    public void createReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportRepository.findAll().size();

        // Create the Report with an existing ID
        report.setId(1L);
        ReportDTO reportDTO = reportMapper.toDto(report);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportMockMvc.perform(post("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportRepository.findAll().size();
        // set the field null
        report.setDescription(null);

        // Create the Report, which fails.
        ReportDTO reportDTO = reportMapper.toDto(report);

        restReportMockMvc.perform(post("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isBadRequest());

        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportRepository.findAll().size();
        // set the field null
        report.setType(null);

        // Create the Report, which fails.
        ReportDTO reportDTO = reportMapper.toDto(report);

        restReportMockMvc.perform(post("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isBadRequest());

        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = reportRepository.findAll().size();
        // set the field null
        report.setCreationDate(null);

        // Create the Report, which fails.
        ReportDTO reportDTO = reportMapper.toDto(report);

        restReportMockMvc.perform(post("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isBadRequest());

        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReports() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList
        restReportMockMvc.perform(get("/api/reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(report.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }
    

    @Test
    @Transactional
    public void getReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get the report
        restReportMockMvc.perform(get("/api/reports/{id}", report.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(report.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllReportsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where title equals to DEFAULT_TITLE
        defaultReportShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the reportList where title equals to UPDATED_TITLE
        defaultReportShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllReportsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultReportShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the reportList where title equals to UPDATED_TITLE
        defaultReportShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllReportsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where title is not null
        defaultReportShouldBeFound("title.specified=true");

        // Get all the reportList where title is null
        defaultReportShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where description equals to DEFAULT_DESCRIPTION
        defaultReportShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the reportList where description equals to UPDATED_DESCRIPTION
        defaultReportShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReportsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultReportShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the reportList where description equals to UPDATED_DESCRIPTION
        defaultReportShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReportsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where description is not null
        defaultReportShouldBeFound("description.specified=true");

        // Get all the reportList where description is null
        defaultReportShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where type equals to DEFAULT_TYPE
        defaultReportShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the reportList where type equals to UPDATED_TYPE
        defaultReportShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllReportsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultReportShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the reportList where type equals to UPDATED_TYPE
        defaultReportShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllReportsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where type is not null
        defaultReportShouldBeFound("type.specified=true");

        // Get all the reportList where type is null
        defaultReportShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportsByCreationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where creationDate equals to DEFAULT_CREATION_DATE
        defaultReportShouldBeFound("creationDate.equals=" + DEFAULT_CREATION_DATE);

        // Get all the reportList where creationDate equals to UPDATED_CREATION_DATE
        defaultReportShouldNotBeFound("creationDate.equals=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllReportsByCreationDateIsInShouldWork() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where creationDate in DEFAULT_CREATION_DATE or UPDATED_CREATION_DATE
        defaultReportShouldBeFound("creationDate.in=" + DEFAULT_CREATION_DATE + "," + UPDATED_CREATION_DATE);

        // Get all the reportList where creationDate equals to UPDATED_CREATION_DATE
        defaultReportShouldNotBeFound("creationDate.in=" + UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void getAllReportsByCreationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        // Get all the reportList where creationDate is not null
        defaultReportShouldBeFound("creationDate.specified=true");

        // Get all the reportList where creationDate is null
        defaultReportShouldNotBeFound("creationDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultReportShouldBeFound(String filter) throws Exception {
        restReportMockMvc.perform(get("/api/reports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(report.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultReportShouldNotBeFound(String filter) throws Exception {
        restReportMockMvc.perform(get("/api/reports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingReport() throws Exception {
        // Get the report
        restReportMockMvc.perform(get("/api/reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        int databaseSizeBeforeUpdate = reportRepository.findAll().size();

        // Update the report
        Report updatedReport = reportRepository.findById(report.getId()).get();
        // Disconnect from session so that the updates on updatedReport are not directly saved in db
        em.detach(updatedReport);
        updatedReport
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .creationDate(UPDATED_CREATION_DATE);
        ReportDTO reportDTO = reportMapper.toDto(updatedReport);

        restReportMockMvc.perform(put("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isOk());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeUpdate);
        Report testReport = reportList.get(reportList.size() - 1);
        assertThat(testReport.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testReport.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testReport.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReport.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingReport() throws Exception {
        int databaseSizeBeforeUpdate = reportRepository.findAll().size();

        // Create the Report
        ReportDTO reportDTO = reportMapper.toDto(report);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReportMockMvc.perform(put("/api/reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportDTO)))
            .andExpect(status().isCreated());

        // Validate the Report in the database
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReport() throws Exception {
        // Initialize the database
        reportRepository.saveAndFlush(report);

        int databaseSizeBeforeDelete = reportRepository.findAll().size();

        // Get the report
        restReportMockMvc.perform(delete("/api/reports/{id}", report.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Report> reportList = reportRepository.findAll();
        assertThat(reportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Report.class);
        Report report1 = new Report();
        report1.setId(1L);
        Report report2 = new Report();
        report2.setId(report1.getId());
        assertThat(report1).isEqualTo(report2);
        report2.setId(2L);
        assertThat(report1).isNotEqualTo(report2);
        report1.setId(null);
        assertThat(report1).isNotEqualTo(report2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportDTO.class);
        ReportDTO reportDTO1 = new ReportDTO();
        reportDTO1.setId(1L);
        ReportDTO reportDTO2 = new ReportDTO();
        assertThat(reportDTO1).isNotEqualTo(reportDTO2);
        reportDTO2.setId(reportDTO1.getId());
        assertThat(reportDTO1).isEqualTo(reportDTO2);
        reportDTO2.setId(2L);
        assertThat(reportDTO1).isNotEqualTo(reportDTO2);
        reportDTO1.setId(null);
        assertThat(reportDTO1).isNotEqualTo(reportDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reportMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reportMapper.fromId(null)).isNull();
    }
}
