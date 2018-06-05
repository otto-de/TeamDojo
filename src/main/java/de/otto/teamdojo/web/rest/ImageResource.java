package de.otto.teamdojo.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.otto.teamdojo.service.ImageService;
import de.otto.teamdojo.web.rest.errors.BadRequestAlertException;
import de.otto.teamdojo.web.rest.util.HeaderUtil;
import de.otto.teamdojo.service.dto.ImageDTO;
import de.otto.teamdojo.service.dto.ImageCriteria;
import de.otto.teamdojo.service.ImageQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Image.
 */
@RestController
@RequestMapping("/api")
public class ImageResource {

    private final Logger log = LoggerFactory.getLogger(ImageResource.class);

    private static final String ENTITY_NAME = "image";

    private final ImageService imageService;

    private final ImageQueryService imageQueryService;

    public ImageResource(ImageService imageService, ImageQueryService imageQueryService) {
        this.imageService = imageService;
        this.imageQueryService = imageQueryService;
    }

    /**
     * POST  /images : Create a new image.
     *
     * @param imageDTO the imageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new imageDTO, or with status 400 (Bad Request) if the image has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/images")
    @Timed
    public ResponseEntity<ImageDTO> createImage(@RequestBody ImageDTO imageDTO) throws URISyntaxException {
        log.debug("REST request to save Image : {}", imageDTO);
        if (imageDTO.getId() != null) {
            throw new BadRequestAlertException("A new image cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImageDTO result = imageService.save(imageDTO);
        return ResponseEntity.created(new URI("/api/images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /images : Updates an existing image.
     *
     * @param imageDTO the imageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated imageDTO,
     * or with status 400 (Bad Request) if the imageDTO is not valid,
     * or with status 500 (Internal Server Error) if the imageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/images")
    @Timed
    public ResponseEntity<ImageDTO> updateImage(@RequestBody ImageDTO imageDTO) throws URISyntaxException {
        log.debug("REST request to update Image : {}", imageDTO);
        if (imageDTO.getId() == null) {
            return createImage(imageDTO);
        }
        ImageDTO result = imageService.save(imageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, imageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /images : get all the images.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of images in body
     */
    @GetMapping("/images")
    @Timed
    public ResponseEntity<List<ImageDTO>> getAllImages(ImageCriteria criteria) {
        log.debug("REST request to get Images by criteria: {}", criteria);
        List<ImageDTO> entityList = imageQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /images/:id : get the "id" image.
     *
     * @param id the id of the imageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/images/{id}")
    @Timed
    public ResponseEntity<ImageDTO> getImage(@PathVariable Long id) {
        log.debug("REST request to get Image : {}", id);
        Optional<ImageDTO> imageDTO = imageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imageDTO);
    }

    /**
     * GET  /images/:id : get the "id" image.
     *
     * @param id the id of the imageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/images/{id}/content")
    @Timed
    public ResponseEntity<byte[]> getImageContent(@PathVariable Long id, @RequestParam(value="size", required=false) String size) {
        log.debug("REST request to get Image : {}", id);
        Optional<ImageDTO> imageDTO = imageService.findOne(id);
        if (!imageDTO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ImageDTO image = imageDTO.get();

        HttpHeaders responseHeaders = new HttpHeaders();
        size = size == null ? "LARGE" : size.toUpperCase();
        byte[] imageBlob;
        String contentType;
        if (size.equals("SMALL")) {
            imageBlob = image.getSmall();
            contentType = image.getSmallContentType();
        } else if (size.equals("MEDIUM")) {
            imageBlob = image.getMedium();
            contentType = image.getMediumContentType();
        } else {
            imageBlob = image.getLarge();
            contentType = image.getLargeContentType();
        }
        responseHeaders.add("Content-Type", contentType);
        return new ResponseEntity<>(imageBlob, responseHeaders, HttpStatus.OK);
    }

    /**
     * DELETE  /images/:id : delete the "id" image.
     *
     * @param id the id of the imageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/images/{id}")
    @Timed
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        log.debug("REST request to delete Image : {}", id);
        imageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
