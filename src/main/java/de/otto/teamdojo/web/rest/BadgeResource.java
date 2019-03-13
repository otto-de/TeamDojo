package de.otto.teamdojo.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.otto.teamdojo.service.BadgeService;
import de.otto.teamdojo.web.rest.errors.BadRequestAlertException;
import de.otto.teamdojo.web.rest.util.HeaderUtil;
import de.otto.teamdojo.web.rest.util.PaginationUtil;
import de.otto.teamdojo.service.dto.BadgeDTO;
import de.otto.teamdojo.service.dto.BadgeCriteria;
import de.otto.teamdojo.service.BadgeQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Badge.
 */
@RestController
@RequestMapping("/api")
public class BadgeResource {

    private final Logger log = LoggerFactory.getLogger(BadgeResource.class);

    private static final String ENTITY_NAME = "badge";

    private final BadgeService badgeService;

    private final BadgeQueryService badgeQueryService;

    public BadgeResource(BadgeService badgeService, BadgeQueryService badgeQueryService) {
        this.badgeService = badgeService;
        this.badgeQueryService = badgeQueryService;
    }

    /**
     * POST  /badges : Create a new badge.
     *
     * @param badgeDTO the badgeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new badgeDTO, or with status 400 (Bad Request) if the badge has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/badges")
    @Timed
    public ResponseEntity<BadgeDTO> createBadge(@Valid @RequestBody BadgeDTO badgeDTO) throws URISyntaxException {
        log.debug("REST request to save Badge : {}", badgeDTO);
        if (badgeDTO.getId() != null) {
            throw new BadRequestAlertException("A new badge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BadgeDTO result = badgeService.save(badgeDTO);
        return ResponseEntity.created(new URI("/api/badges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /badges : Updates an existing badge.
     *
     * @param badgeDTO the badgeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated badgeDTO,
     * or with status 400 (Bad Request) if the badgeDTO is not valid,
     * or with status 500 (Internal Server Error) if the badgeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/badges")
    @Timed
    public ResponseEntity<BadgeDTO> updateBadge(@Valid @RequestBody BadgeDTO badgeDTO) throws URISyntaxException {
        log.debug("REST request to update Badge : {}", badgeDTO);
        if (badgeDTO.getId() == null) {
            return createBadge(badgeDTO);
        }
        BadgeDTO result = badgeService.save(badgeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, badgeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /badges : get all the badges.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of badges in body
     */
    @GetMapping("/badges")
    @Timed
    public ResponseEntity<List<BadgeDTO>> getAllBadges(BadgeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Badges by criteria: {}", criteria);
        Page<BadgeDTO> page = badgeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/badges");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /badges/:id : get the "id" badge.
     *
     * @param id the id of the badgeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the badgeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/badges/{id}")
    @Timed
    public ResponseEntity<BadgeDTO> getBadge(@PathVariable Long id) {
        log.debug("REST request to get Badge : {}", id);
        Optional<BadgeDTO> badgeDTO = badgeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(badgeDTO);
    }

    /**
     * DELETE  /badges/:id : delete the "id" badge.
     *
     * @param id the id of the badgeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/badges/{id}")
    @Timed
    public ResponseEntity<Void> deleteBadge(@PathVariable Long id) {
        log.debug("REST request to delete Badge : {}", id);
        badgeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
