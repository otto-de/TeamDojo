package de.otto.teamdojo.service;

import de.otto.teamdojo.domain.BadgeSkill;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing BadgeSkill.
 */
public interface BadgeSkillService {

    /**
     * Save a badgeSkill.
     *
     * @param badgeSkill the entity to save
     * @return the persisted entity
     */
    BadgeSkill save(BadgeSkill badgeSkill);

    /**
     * Get all the badgeSkills.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BadgeSkill> findAll(Pageable pageable);


    /**
     * Get the "id" badgeSkill.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BadgeSkill> findOne(Long id);

    /**
     * Delete the "id" badgeSkill.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
