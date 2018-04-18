package de.otto.dojo.service;

import de.otto.dojo.domain.Dimension;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Dimension.
 */
public interface DimensionService {

    /**
     * Save a dimension.
     *
     * @param dimension the entity to save
     * @return the persisted entity
     */
    Dimension save(Dimension dimension);

    /**
     * Get all the dimensions.
     *
     * @return the list of entities
     */
    List<Dimension> findAll();


    /**
     * Get the "id" dimension.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Dimension> findOne(Long id);

    /**
     * Delete the "id" dimension.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
