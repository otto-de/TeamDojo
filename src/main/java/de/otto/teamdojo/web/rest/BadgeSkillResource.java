package de.otto.teamdojo.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.otto.teamdojo.domain.BadgeSkill;
import de.otto.teamdojo.service.BadgeSkillService;
import de.otto.teamdojo.web.rest.errors.BadRequestAlertException;
import de.otto.teamdojo.web.rest.util.HeaderUtil;
import de.otto.teamdojo.web.rest.util.PaginationUtil;
import de.otto.teamdojo.service.dto.BadgeSkillCriteria;
import de.otto.teamdojo.service.BadgeSkillQueryService;
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
 * REST controller for managing BadgeSkill.
 */
@RestController
@RequestMapping("/api")
public class BadgeSkillResource {

    private final Logger log = LoggerFactory.getLogger(BadgeSkillResource.class);

    private static final String ENTITY_NAME = "badgeSkill";

    private final BadgeSkillService badgeSkillService;

    private final BadgeSkillQueryService badgeSkillQueryService;

    public BadgeSkillResource(BadgeSkillService badgeSkillService, BadgeSkillQueryService badgeSkillQueryService) {
        this.badgeSkillService = badgeSkillService;
        this.badgeSkillQueryService = badgeSkillQueryService;
    }

    /**
     * POST  /badge-skills : Create a new badgeSkill.
     *
     * @param badgeSkill the badgeSkill to create
     * @return the ResponseEntity with status 201 (Created) and with body the new badgeSkill, or with status 400 (Bad Request) if the badgeSkill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/badge-skills")
    @Timed
    public ResponseEntity<BadgeSkill> createBadgeSkill(@Valid @RequestBody BadgeSkill badgeSkill) throws URISyntaxException {
        log.debug("REST request to save BadgeSkill : {}", badgeSkill);
        if (badgeSkill.getId() != null) {
            throw new BadRequestAlertException("A new badgeSkill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BadgeSkill result = badgeSkillService.save(badgeSkill);
        return ResponseEntity.created(new URI("/api/badge-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /badge-skills : Updates an existing badgeSkill.
     *
     * @param badgeSkill the badgeSkill to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated badgeSkill,
     * or with status 400 (Bad Request) if the badgeSkill is not valid,
     * or with status 500 (Internal Server Error) if the badgeSkill couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/badge-skills")
    @Timed
    public ResponseEntity<BadgeSkill> updateBadgeSkill(@Valid @RequestBody BadgeSkill badgeSkill) throws URISyntaxException {
        log.debug("REST request to update BadgeSkill : {}", badgeSkill);
        if (badgeSkill.getId() == null) {
            return createBadgeSkill(badgeSkill);
        }
        BadgeSkill result = badgeSkillService.save(badgeSkill);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, badgeSkill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /badge-skills : get all the badgeSkills.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of badgeSkills in body
     */
    @GetMapping("/badge-skills")
    @Timed
    public ResponseEntity<List<BadgeSkill>> getAllBadgeSkills(BadgeSkillCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BadgeSkills by criteria: {}", criteria);
        Page<BadgeSkill> page = badgeSkillQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/badge-skills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /badge-skills/:id : get the "id" badgeSkill.
     *
     * @param id the id of the badgeSkill to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the badgeSkill, or with status 404 (Not Found)
     */
    @GetMapping("/badge-skills/{id}")
    @Timed
    public ResponseEntity<BadgeSkill> getBadgeSkill(@PathVariable Long id) {
        log.debug("REST request to get BadgeSkill : {}", id);
        Optional<BadgeSkill> badgeSkill = badgeSkillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(badgeSkill);
    }

    /**
     * DELETE  /badge-skills/:id : delete the "id" badgeSkill.
     *
     * @param id the id of the badgeSkill to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/badge-skills/{id}")
    @Timed
    public ResponseEntity<Void> deleteBadgeSkill(@PathVariable Long id) {
        log.debug("REST request to delete BadgeSkill : {}", id);
        badgeSkillService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
