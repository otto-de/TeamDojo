package de.otto.teamdojo.service;

import de.otto.teamdojo.service.dto.DimensionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Dimension.
 */
public interface DimensionService {

    /**
     * Save a dimension.
     *
     * @param dimensionDTO the entity to save
     * @return the persisted entity
     */
    DimensionDTO save(DimensionDTO dimensionDTO);

    /**
     * Get all the dimensions.
     *
     * @return the list of entities
     */
    List<DimensionDTO> findAll();


    /**
     * Get the "id" dimension.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DimensionDTO> findOne(Long id);

    /**
     * Delete the "id" dimension.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
