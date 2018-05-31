package de.otto.teamdojo.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.otto.teamdojo.service.LevelQueryService;
import de.otto.teamdojo.service.LevelService;
import de.otto.teamdojo.service.LevelSkillService;
import de.otto.teamdojo.service.dto.LevelCriteria;
import de.otto.teamdojo.service.dto.LevelDTO;
import de.otto.teamdojo.service.dto.LevelSkillDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Level.
 */
@RestController
@RequestMapping("/api")
public class LevelResource {

    private final Logger log = LoggerFactory.getLogger(LevelResource.class);

    private static final String ENTITY_NAME = "level";

    private final LevelService levelService;

    private final LevelQueryService levelQueryService;

    private final LevelSkillService levelSkillService;

    public LevelResource(LevelService levelService, LevelQueryService levelQueryService, LevelSkillService levelSkillService) {
        this.levelService = levelService;
        this.levelQueryService = levelQueryService;
        this.levelSkillService = levelSkillService;
    }

    /**
     * POST  /levels : Create a new level.
     *
     * @param levelDTO the levelDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new levelDTO, or with status 400 (Bad Request) if the level has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/levels")
    @Timed
    public ResponseEntity<LevelDTO> createLevel(@Valid @RequestBody LevelDTO levelDTO) throws URISyntaxException {
        log.debug("REST request to save Level : {}", levelDTO);
        if (levelDTO.getId() != null) {
            throw new BadRequestAlertException("A new level cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LevelDTO result = levelService.save(levelDTO);
        return ResponseEntity.created(new URI("/api/levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /levels : Updates an existing level.
     *
     * @param levelDTO the levelDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated levelDTO,
     * or with status 400 (Bad Request) if the levelDTO is not valid,
     * or with status 500 (Internal Server Error) if the levelDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/levels")
    @Timed
    public ResponseEntity<LevelDTO> updateLevel(@Valid @RequestBody LevelDTO levelDTO) throws URISyntaxException {
        log.debug("REST request to update Level : {}", levelDTO);
        if (levelDTO.getId() == null) {
            return createLevel(levelDTO);
        }
        LevelDTO result = levelService.save(levelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, levelDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /levels : get all the levels.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of levels in body
     */
    @GetMapping("/levels")
    @Timed
    public ResponseEntity<List<LevelDTO>> getAllLevels(LevelCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Levels by criteria: {}", criteria);

        if(criteria != null && criteria.getSkillsId() != null && criteria.getSkillsId().getIn() != null)
            return getAllLevelsBySkills(criteria.getSkillsId().getIn(), pageable);

        List<LevelDTO> entityList = levelQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }


    /**
     * GET  /levels : get all the levels.
     *
     * @param skillsId the skillsId to search for
     * @return the ResponseEntity with status 200 (OK) and the list of levels in body
     */
    public ResponseEntity<List<LevelDTO>> getAllLevelsBySkills(
        List<Long> skillsId,
        Pageable pageable) {
        log.debug("REST request to get Levels for Skills; {}", skillsId);

        List<LevelSkillDTO> levelSkills = levelSkillService.findBySkillIdIn(skillsId, pageable);
        List<Long> levelIds = new ArrayList<>();
        for(LevelSkillDTO levelSkill : levelSkills){
            levelIds.add(levelSkill.getLevelId());
        }

        Page<LevelDTO> page = levelService.findByIdIn(levelIds, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/levels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /levels/:id : get the "id" level.
     *
     * @param id the id of the levelDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the levelDTO, or with status 404 (Not Found)
     */
    @GetMapping("/levels/{id}")
    @Timed
    public ResponseEntity<LevelDTO> getLevel(@PathVariable Long id) {
        log.debug("REST request to get Level : {}", id);
        Optional<LevelDTO> levelDTO = levelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(levelDTO);
    }

    /**
     * DELETE  /levels/:id : delete the "id" level.
     *
     * @param id the id of the levelDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/levels/{id}")
    @Timed
    public ResponseEntity<Void> deleteLevel(@PathVariable Long id) {
        log.debug("REST request to delete Level : {}", id);
        levelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
