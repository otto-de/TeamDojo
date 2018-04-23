package de.otto.teamdojo.service;

import de.otto.teamdojo.domain.Skill;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Skill.
 */
public interface SkillService {

    /**
     * Save a skill.
     *
     * @param skill the entity to save
     * @return the persisted entity
     */
    Skill save(Skill skill);

    /**
     * Get all the skills.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Skill> findAll(Pageable pageable);


    /**
     * Get the "id" skill.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Skill> findOne(Long id);

    /**
     * Delete the "id" skill.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
