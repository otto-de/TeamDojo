package de.otto.teamdojo.service.impl;

import de.otto.teamdojo.service.TeamSkillService;
import de.otto.teamdojo.domain.TeamSkill;
import de.otto.teamdojo.repository.TeamSkillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TeamSkill.
 */
@Service
@Transactional
public class TeamSkillServiceImpl implements TeamSkillService {

    private final Logger log = LoggerFactory.getLogger(TeamSkillServiceImpl.class);

    private final TeamSkillRepository teamSkillRepository;

    public TeamSkillServiceImpl(TeamSkillRepository teamSkillRepository) {
        this.teamSkillRepository = teamSkillRepository;
    }

    /**
     * Save a teamSkill.
     *
     * @param teamSkill the entity to save
     * @return the persisted entity
     */
    @Override
    public TeamSkill save(TeamSkill teamSkill) {
        log.debug("Request to save TeamSkill : {}", teamSkill);
        return teamSkillRepository.save(teamSkill);
    }

    /**
     * Get all the teamSkills.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeamSkill> findAll(Pageable pageable) {
        log.debug("Request to get all TeamSkills");
        return teamSkillRepository.findAll(pageable);
    }


    /**
     * Get one teamSkill by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TeamSkill> findOne(Long id) {
        log.debug("Request to get TeamSkill : {}", id);
        return teamSkillRepository.findById(id);
    }

    /**
     * Delete the teamSkill by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TeamSkill : {}", id);
        teamSkillRepository.deleteById(id);
    }
}
