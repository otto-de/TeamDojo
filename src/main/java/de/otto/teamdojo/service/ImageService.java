package de.otto.teamdojo.service;

import de.otto.teamdojo.service.dto.ImageDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Image.
 */
public interface ImageService {

    /**
     * Save a image.
     *
     * @param imageDTO the entity to save
     * @return the persisted entity
     */
    ImageDTO save(ImageDTO imageDTO);

    /**
     * Get all the images.
     *
     * @return the list of entities
     */
    List<ImageDTO> findAll();


    /**
     * Get the "id" image.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ImageDTO> findOne(Long id);

    /**
     * Get the "name" image.
     *
     * @param name the name of the entity
     * @return the entity
     */
    Optional<ImageDTO> findByName(String name);

    /**
     * Delete the "id" image.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
