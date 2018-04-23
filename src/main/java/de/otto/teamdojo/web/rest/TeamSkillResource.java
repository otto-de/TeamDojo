package de.otto.teamdojo.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.otto.teamdojo.domain.TeamSkill;
import de.otto.teamdojo.service.TeamSkillQueryService;
import de.otto.teamdojo.service.TeamSkillService;
import de.otto.teamdojo.service.dto.TeamSkillCriteria;
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
 * REST controller for managing TeamSkill.
 */
@RestController
@RequestMapping("/api")
public class TeamSkillResource {

    private final Logger log = LoggerFactory.getLogger(TeamSkillResource.class);

    private static final String ENTITY_NAME = "teamSkill";

    private final TeamSkillService teamSkillService;

    private final TeamSkillQueryService teamSkillQueryService;

    public TeamSkillResource(TeamSkillService teamSkillService, TeamSkillQueryService teamSkillQueryService) {
        this.teamSkillService = teamSkillService;
        this.teamSkillQueryService = teamSkillQueryService;
    }

    /**
     * POST  /team-skills : Create a new teamSkill.
     *
     * @param teamSkill the teamSkill to create
     * @return the ResponseEntity with status 201 (Created) and with body the new teamSkill, or with status 400 (Bad Request) if the teamSkill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/team-skills")
    @Timed
    public ResponseEntity<TeamSkill> createTeamSkill(@Valid @RequestBody TeamSkill teamSkill) throws URISyntaxException {
        log.debug("REST request to save TeamSkill : {}", teamSkill);
        if (teamSkill.getId() != null) {
            throw new BadRequestAlertException("A new teamSkill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeamSkill result = teamSkillService.save(teamSkill);
        return ResponseEntity.created(new URI("/api/team-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /team-skills : Updates an existing teamSkill.
     *
     * @param teamSkill the teamSkill to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated teamSkill,
     * or with status 400 (Bad Request) if the teamSkill is not valid,
     * or with status 500 (Internal Server Error) if the teamSkill couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/team-skills")
    @Timed
    public ResponseEntity<TeamSkill> updateTeamSkill(@Valid @RequestBody TeamSkill teamSkill) throws URISyntaxException {
        log.debug("REST request to update TeamSkill : {}", teamSkill);
        if (teamSkill.getId() == null) {
            return createTeamSkill(teamSkill);
        }
        TeamSkill result = teamSkillService.save(teamSkill);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, teamSkill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /team-skills : get all the teamSkills.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of teamSkills in body
     */
    @GetMapping("/team-skills")
    @Timed
    public ResponseEntity<List<TeamSkill>> getAllTeamSkills(TeamSkillCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TeamSkills by criteria: {}", criteria);
        Page<TeamSkill> page = teamSkillQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/team-skills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /team-skills/:id : get the "id" teamSkill.
     *
     * @param id the id of the teamSkill to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the teamSkill, or with status 404 (Not Found)
     */
    @GetMapping("/team-skills/{id}")
    @Timed
    public ResponseEntity<TeamSkill> getTeamSkill(@PathVariable Long id) {
        log.debug("REST request to get TeamSkill : {}", id);
        Optional<TeamSkill> teamSkill = teamSkillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teamSkill);
    }

    /**
     * DELETE  /team-skills/:id : delete the "id" teamSkill.
     *
     * @param id the id of the teamSkill to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/team-skills/{id}")
    @Timed
    public ResponseEntity<Void> deleteTeamSkill(@PathVariable Long id) {
        log.debug("REST request to delete TeamSkill : {}", id);
        teamSkillService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
