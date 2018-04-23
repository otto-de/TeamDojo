package de.otto.teamdojo.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.otto.teamdojo.domain.Dimension;
import de.otto.teamdojo.service.DimensionService;
import de.otto.teamdojo.web.rest.errors.BadRequestAlertException;
import de.otto.teamdojo.web.rest.util.HeaderUtil;
import de.otto.teamdojo.service.dto.DimensionCriteria;
import de.otto.teamdojo.service.DimensionQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Dimension.
 */
@RestController
@RequestMapping("/api")
public class DimensionResource {

    private final Logger log = LoggerFactory.getLogger(DimensionResource.class);

    private static final String ENTITY_NAME = "dimension";

    private final DimensionService dimensionService;

    private final DimensionQueryService dimensionQueryService;

    public DimensionResource(DimensionService dimensionService, DimensionQueryService dimensionQueryService) {
        this.dimensionService = dimensionService;
        this.dimensionQueryService = dimensionQueryService;
    }

    /**
     * POST  /dimensions : Create a new dimension.
     *
     * @param dimension the dimension to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dimension, or with status 400 (Bad Request) if the dimension has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dimensions")
    @Timed
    public ResponseEntity<Dimension> createDimension(@Valid @RequestBody Dimension dimension) throws URISyntaxException {
        log.debug("REST request to save Dimension : {}", dimension);
        if (dimension.getId() != null) {
            throw new BadRequestAlertException("A new dimension cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dimension result = dimensionService.save(dimension);
        return ResponseEntity.created(new URI("/api/dimensions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dimensions : Updates an existing dimension.
     *
     * @param dimension the dimension to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dimension,
     * or with status 400 (Bad Request) if the dimension is not valid,
     * or with status 500 (Internal Server Error) if the dimension couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dimensions")
    @Timed
    public ResponseEntity<Dimension> updateDimension(@Valid @RequestBody Dimension dimension) throws URISyntaxException {
        log.debug("REST request to update Dimension : {}", dimension);
        if (dimension.getId() == null) {
            return createDimension(dimension);
        }
        Dimension result = dimensionService.save(dimension);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dimension.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dimensions : get all the dimensions.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of dimensions in body
     */
    @GetMapping("/dimensions")
    @Timed
    public ResponseEntity<List<Dimension>> getAllDimensions(DimensionCriteria criteria) {
        log.debug("REST request to get Dimensions by criteria: {}", criteria);
        List<Dimension> entityList = dimensionQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /dimensions/:id : get the "id" dimension.
     *
     * @param id the id of the dimension to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dimension, or with status 404 (Not Found)
     */
    @GetMapping("/dimensions/{id}")
    @Timed
    public ResponseEntity<Dimension> getDimension(@PathVariable Long id) {
        log.debug("REST request to get Dimension : {}", id);
        Optional<Dimension> dimension = dimensionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dimension);
    }

    /**
     * DELETE  /dimensions/:id : delete the "id" dimension.
     *
     * @param id the id of the dimension to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dimensions/{id}")
    @Timed
    public ResponseEntity<Void> deleteDimension(@PathVariable Long id) {
        log.debug("REST request to delete Dimension : {}", id);
        dimensionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
