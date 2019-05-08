package de.otto.teamdojo.web.rest;


import de.otto.teamdojo.service.BadgeSkillQueryService;
import de.otto.teamdojo.service.BadgeSkillService;
import de.otto.teamdojo.service.dto.BadgeSkillCriteria;
import de.otto.teamdojo.service.dto.BadgeSkillDTO;
import de.otto.teamdojo.web.rest.errors.BadRequestAlertException;
import de.otto.teamdojo.web.rest.util.HeaderUtil;
import de.otto.teamdojo.web.rest.util.PaginationUtil;
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

    private static final String ENTITY_NAME = "badgeSkill";
    private final Logger log = LoggerFactory.getLogger(BadgeSkillResource.class);
    private final BadgeSkillService badgeSkillService;

    private final BadgeSkillQueryService badgeSkillQueryService;

    public BadgeSkillResource(BadgeSkillService badgeSkillService, BadgeSkillQueryService badgeSkillQueryService) {
        this.badgeSkillService = badgeSkillService;
        this.badgeSkillQueryService = badgeSkillQueryService;
    }

    /**
     * POST  /badge-skills : Create a new badgeSkill.
     *
     * @param badgeSkillDTO the badgeSkillDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new badgeSkillDTO, or with status 400 (Bad Request) if the badgeSkill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/badge-skills")
    public ResponseEntity<BadgeSkillDTO> createBadgeSkill(@Valid @RequestBody BadgeSkillDTO badgeSkillDTO) throws URISyntaxException {
        log.debug("REST request to save BadgeSkill : {}", badgeSkillDTO);
        if (badgeSkillDTO.getId() != null) {
            throw new BadRequestAlertException("A new badgeSkill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BadgeSkillDTO result = badgeSkillService.save(badgeSkillDTO);
        return ResponseEntity.created(new URI("/api/badge-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /badge-skills : Updates an existing badgeSkill.
     *
     * @param badgeSkillDTO the badgeSkillDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated badgeSkillDTO,
     * or with status 400 (Bad Request) if the badgeSkillDTO is not valid,
     * or with status 500 (Internal Server Error) if the badgeSkillDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/badge-skills")
    public ResponseEntity<BadgeSkillDTO> updateBadgeSkill(@Valid @RequestBody BadgeSkillDTO badgeSkillDTO) throws URISyntaxException {
        log.debug("REST request to update BadgeSkill : {}", badgeSkillDTO);
        if (badgeSkillDTO.getId() == null) {
            return createBadgeSkill(badgeSkillDTO);
        }
        BadgeSkillDTO result = badgeSkillService.save(badgeSkillDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, badgeSkillDTO.getId().toString()))
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
    public ResponseEntity<List<BadgeSkillDTO>> getAllBadgeSkills(BadgeSkillCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BadgeSkills by criteria: {}", criteria);
        Page<BadgeSkillDTO> page = badgeSkillQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/badge-skills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /badge-skills/:id : get the "id" badgeSkill.
     *
     * @param id the id of the badgeSkillDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the badgeSkillDTO, or with status 404 (Not Found)
     */
    @GetMapping("/badge-skills/{id}")
    public ResponseEntity<BadgeSkillDTO> getBadgeSkill(@PathVariable Long id) {
        log.debug("REST request to get BadgeSkill : {}", id);
        Optional<BadgeSkillDTO> badgeSkillDTO = badgeSkillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(badgeSkillDTO);
    }

    /**
     * DELETE  /badge-skills/:id : delete the "id" badgeSkill.
     *
     * @param id the id of the badgeSkillDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/badge-skills/{id}")
    public ResponseEntity<Void> deleteBadgeSkill(@PathVariable Long id) {
        log.debug("REST request to delete BadgeSkill : {}", id);
        badgeSkillService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
