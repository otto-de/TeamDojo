package de.otto.teamdojo.service;

import de.otto.teamdojo.domain.Level;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Level.
 */
public interface LevelService {

    /**
     * Save a level.
     *
     * @param level the entity to save
     * @return the persisted entity
     */
    Level save(Level level);

    /**
     * Get all the levels.
     *
     * @return the list of entities
     */
    List<Level> findAll();


    /**
     * Get the "id" level.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Level> findOne(Long id);

    /**
     * Delete the "id" level.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
