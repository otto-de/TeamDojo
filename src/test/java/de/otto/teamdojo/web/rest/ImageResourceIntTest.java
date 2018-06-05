package de.otto.teamdojo.web.rest;

import de.otto.teamdojo.TeamdojoApp;

import de.otto.teamdojo.domain.Image;
import de.otto.teamdojo.repository.ImageRepository;
import de.otto.teamdojo.service.ImageService;
import de.otto.teamdojo.service.dto.ImageDTO;
import de.otto.teamdojo.service.mapper.ImageMapper;
import de.otto.teamdojo.web.rest.errors.ExceptionTranslator;
import de.otto.teamdojo.service.dto.ImageCriteria;
import de.otto.teamdojo.service.ImageQueryService;

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
import java.util.List;
import java.util.ArrayList;

import static de.otto.teamdojo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ImageResource REST controller.
 *
 * @see ImageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TeamdojoApp.class)
public class ImageResourceIntTest {

    private static final byte[] DEFAULT_SMALL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SMALL = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_SMALL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SMALL_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_MEDIUM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MEDIUM = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_MEDIUM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MEDIUM_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_LARGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LARGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_LARGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LARGE_CONTENT_TYPE = "image/png";

    @Autowired
    private ImageRepository imageRepository;



    @Autowired
    private ImageMapper imageMapper;
    

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageQueryService imageQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restImageMockMvc;

    private Image image;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImageResource imageResource = new ImageResource(imageService, imageQueryService);
        this.restImageMockMvc = MockMvcBuilders.standaloneSetup(imageResource)
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
    public static Image createEntity(EntityManager em) {
        Image image = new Image()
            .small(DEFAULT_SMALL)
            .smallContentType(DEFAULT_SMALL_CONTENT_TYPE)
            .medium(DEFAULT_MEDIUM)
            .mediumContentType(DEFAULT_MEDIUM_CONTENT_TYPE)
            .large(DEFAULT_LARGE)
            .largeContentType(DEFAULT_LARGE_CONTENT_TYPE);
        return image;
    }

    @Before
    public void initTest() {
        image = createEntity(em);
    }

    @Test
    @Transactional
    public void createImage() throws Exception {
        int databaseSizeBeforeCreate = imageRepository.findAll().size();

        // Create the Image
        ImageDTO imageDTO = imageMapper.toDto(image);
        restImageMockMvc.perform(post("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isCreated());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeCreate + 1);
        Image testImage = imageList.get(imageList.size() - 1);
        assertThat(testImage.getSmall()).isEqualTo(DEFAULT_SMALL);
        assertThat(testImage.getSmallContentType()).isEqualTo(DEFAULT_SMALL_CONTENT_TYPE);
        assertThat(testImage.getMedium()).isEqualTo(DEFAULT_MEDIUM);
        assertThat(testImage.getMediumContentType()).isEqualTo(DEFAULT_MEDIUM_CONTENT_TYPE);
        assertThat(testImage.getLarge()).isEqualTo(DEFAULT_LARGE);
        assertThat(testImage.getLargeContentType()).isEqualTo(DEFAULT_LARGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imageRepository.findAll().size();

        // Create the Image with an existing ID
        image.setId(1L);
        ImageDTO imageDTO = imageMapper.toDto(image);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImageMockMvc.perform(post("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllImages() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get all the imageList
        restImageMockMvc.perform(get("/api/images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(image.getId().intValue())))
            .andExpect(jsonPath("$.[*].smallContentType").value(hasItem(DEFAULT_SMALL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].small").value(hasItem(Base64Utils.encodeToString(DEFAULT_SMALL))))
            .andExpect(jsonPath("$.[*].mediumContentType").value(hasItem(DEFAULT_MEDIUM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].medium").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIUM))))
            .andExpect(jsonPath("$.[*].largeContentType").value(hasItem(DEFAULT_LARGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].large").value(hasItem(Base64Utils.encodeToString(DEFAULT_LARGE))));
    }
    

    @Test
    @Transactional
    public void getImage() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        // Get the image
        restImageMockMvc.perform(get("/api/images/{id}", image.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(image.getId().intValue()))
            .andExpect(jsonPath("$.smallContentType").value(DEFAULT_SMALL_CONTENT_TYPE))
            .andExpect(jsonPath("$.small").value(Base64Utils.encodeToString(DEFAULT_SMALL)))
            .andExpect(jsonPath("$.mediumContentType").value(DEFAULT_MEDIUM_CONTENT_TYPE))
            .andExpect(jsonPath("$.medium").value(Base64Utils.encodeToString(DEFAULT_MEDIUM)))
            .andExpect(jsonPath("$.largeContentType").value(DEFAULT_LARGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.large").value(Base64Utils.encodeToString(DEFAULT_LARGE)));
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultImageShouldBeFound(String filter) throws Exception {
        restImageMockMvc.perform(get("/api/images?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(image.getId().intValue())))
            .andExpect(jsonPath("$.[*].smallContentType").value(hasItem(DEFAULT_SMALL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].small").value(hasItem(Base64Utils.encodeToString(DEFAULT_SMALL))))
            .andExpect(jsonPath("$.[*].mediumContentType").value(hasItem(DEFAULT_MEDIUM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].medium").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIUM))))
            .andExpect(jsonPath("$.[*].largeContentType").value(hasItem(DEFAULT_LARGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].large").value(hasItem(Base64Utils.encodeToString(DEFAULT_LARGE))));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultImageShouldNotBeFound(String filter) throws Exception {
        restImageMockMvc.perform(get("/api/images?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingImage() throws Exception {
        // Get the image
        restImageMockMvc.perform(get("/api/images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImage() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        int databaseSizeBeforeUpdate = imageRepository.findAll().size();

        // Update the image
        Image updatedImage = imageRepository.findById(image.getId()).get();
        // Disconnect from session so that the updates on updatedImage are not directly saved in db
        em.detach(updatedImage);
        updatedImage
            .small(UPDATED_SMALL)
            .smallContentType(UPDATED_SMALL_CONTENT_TYPE)
            .medium(UPDATED_MEDIUM)
            .mediumContentType(UPDATED_MEDIUM_CONTENT_TYPE)
            .large(UPDATED_LARGE)
            .largeContentType(UPDATED_LARGE_CONTENT_TYPE);
        ImageDTO imageDTO = imageMapper.toDto(updatedImage);

        restImageMockMvc.perform(put("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isOk());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
        Image testImage = imageList.get(imageList.size() - 1);
        assertThat(testImage.getSmall()).isEqualTo(UPDATED_SMALL);
        assertThat(testImage.getSmallContentType()).isEqualTo(UPDATED_SMALL_CONTENT_TYPE);
        assertThat(testImage.getMedium()).isEqualTo(UPDATED_MEDIUM);
        assertThat(testImage.getMediumContentType()).isEqualTo(UPDATED_MEDIUM_CONTENT_TYPE);
        assertThat(testImage.getLarge()).isEqualTo(UPDATED_LARGE);
        assertThat(testImage.getLargeContentType()).isEqualTo(UPDATED_LARGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingImage() throws Exception {
        int databaseSizeBeforeUpdate = imageRepository.findAll().size();

        // Create the Image
        ImageDTO imageDTO = imageMapper.toDto(image);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restImageMockMvc.perform(put("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isCreated());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteImage() throws Exception {
        // Initialize the database
        imageRepository.saveAndFlush(image);

        int databaseSizeBeforeDelete = imageRepository.findAll().size();

        // Get the image
        restImageMockMvc.perform(delete("/api/images/{id}", image.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Image.class);
        Image image1 = new Image();
        image1.setId(1L);
        Image image2 = new Image();
        image2.setId(image1.getId());
        assertThat(image1).isEqualTo(image2);
        image2.setId(2L);
        assertThat(image1).isNotEqualTo(image2);
        image1.setId(null);
        assertThat(image1).isNotEqualTo(image2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImageDTO.class);
        ImageDTO imageDTO1 = new ImageDTO();
        imageDTO1.setId(1L);
        ImageDTO imageDTO2 = new ImageDTO();
        assertThat(imageDTO1).isNotEqualTo(imageDTO2);
        imageDTO2.setId(imageDTO1.getId());
        assertThat(imageDTO1).isEqualTo(imageDTO2);
        imageDTO2.setId(2L);
        assertThat(imageDTO1).isNotEqualTo(imageDTO2);
        imageDTO1.setId(null);
        assertThat(imageDTO1).isNotEqualTo(imageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(imageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(imageMapper.fromId(null)).isNull();
    }
}
