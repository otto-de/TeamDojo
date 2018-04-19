package de.otto.dojo.service;

import de.otto.dojo.domain.TeamSkill;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TeamSkill.
 */
public interface TeamSkillService {

    /**
     * Save a teamSkill.
     *
     * @param teamSkill the entity to save
     * @return the persisted entity
     */
    TeamSkill save(TeamSkill teamSkill);

    /**
     * Get all the teamSkills.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TeamSkill> findAll(Pageable pageable);


    /**
     * Get the "id" teamSkill.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TeamSkill> findOne(Long id);

    /**
     * Delete the "id" teamSkill.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
