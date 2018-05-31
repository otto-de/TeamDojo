package de.otto.teamdojo.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.otto.teamdojo.service.SkillQueryService;
import de.otto.teamdojo.service.SkillService;
import de.otto.teamdojo.service.dto.SkillCriteria;
import de.otto.teamdojo.service.dto.SkillDTO;
import de.otto.teamdojo.service.dto.SkillRateDTO;
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
 * REST controller for managing Skill.
 */
@RestController
@RequestMapping("/api")
public class SkillResource {

    private final Logger log = LoggerFactory.getLogger(SkillResource.class);

    private static final String ENTITY_NAME = "skill";

    private final SkillService skillService;

    private final SkillQueryService skillQueryService;

    public SkillResource(SkillService skillService, SkillQueryService skillQueryService) {
        this.skillService = skillService;
        this.skillQueryService = skillQueryService;
    }

    /**
     * POST  /skills : Create a new skill.
     *
     * @param skillDTO the skillDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skillDTO, or with status 400 (Bad Request) if the skill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/skills")
    @Timed
    public ResponseEntity<SkillDTO> createSkill(@Valid @RequestBody SkillDTO skillDTO) throws URISyntaxException {
        log.debug("REST request to save Skill : {}", skillDTO);
        if (skillDTO.getId() != null) {
            throw new BadRequestAlertException("A new skill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkillDTO result = skillService.save(skillDTO);
        return ResponseEntity.created(new URI("/api/skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skills : Updates an existing skill.
     *
     * @param skillDTO the skillDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated skillDTO,
     * or with status 400 (Bad Request) if the skillDTO is not valid,
     * or with status 500 (Internal Server Error) if the skillDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/skills")
    @Timed
    public ResponseEntity<SkillDTO> updateSkill(@Valid @RequestBody SkillDTO skillDTO) throws URISyntaxException {
        log.debug("REST request to update Skill : {}", skillDTO);
        if (skillDTO.getId() == null) {
            return createSkill(skillDTO);
        }
        SkillDTO result = skillService.save(skillDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, skillDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skills : get all the skills.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of skills in body
     */
    @GetMapping("/skills")
    @Timed
    public ResponseEntity<List<SkillDTO>> getAllSkills(SkillCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Skills by criteria: {}", criteria);
        Page<SkillDTO> page = skillQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/skills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /skills/:id : get the "id" skill.
     *
     * @param id the id of the skillDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the skillDTO, or with status 404 (Not Found)
     */
    @GetMapping("/skills/{id}")
    @Timed
    public ResponseEntity<SkillDTO> getSkill(@PathVariable Long id) {
        log.debug("REST request to get Skill : {}", id);
        Optional<SkillDTO> skillDTO = skillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skillDTO);
    }

    /**
     * DELETE  /skills/:id : delete the "id" skill.
     *
     * @param id the id of the skillDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/skills/{id}")
    @Timed
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        log.debug("REST request to delete Skill : {}", id);
        skillService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * POST /skill/:id : update the "id" skill.
     *
     * @param id the id of the skillDTO to update
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/skills/{id}/vote")
    @Timed
    public ResponseEntity<SkillDTO> createVote(@PathVariable Long id, @Valid @RequestBody SkillRateDTO rateDto){
        log.debug("REST request to create a new vote for Skill : {}", id);
        SkillDTO result = skillService.createVote(id, rateDto.getRateScore());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
            .body(result);
    }
}
