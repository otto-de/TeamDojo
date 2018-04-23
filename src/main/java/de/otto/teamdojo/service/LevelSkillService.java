package de.otto.teamdojo.service;

import de.otto.teamdojo.domain.LevelSkill;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing LevelSkill.
 */
public interface LevelSkillService {

    /**
     * Save a levelSkill.
     *
     * @param levelSkill the entity to save
     * @return the persisted entity
     */
    LevelSkill save(LevelSkill levelSkill);

    /**
     * Get all the levelSkills.
     *
     * @return the list of entities
     */
    List<LevelSkill> findAll();


    /**
     * Get the "id" levelSkill.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LevelSkill> findOne(Long id);

    /**
     * Delete the "id" levelSkill.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
