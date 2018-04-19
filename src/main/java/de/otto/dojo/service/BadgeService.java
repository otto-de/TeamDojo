package de.otto.dojo.service;

import de.otto.dojo.domain.Badge;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Badge.
 */
public interface BadgeService {

    /**
     * Save a badge.
     *
     * @param badge the entity to save
     * @return the persisted entity
     */
    Badge save(Badge badge);

    /**
     * Get all the badges.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Badge> findAll(Pageable pageable);


    /**
     * Get the "id" badge.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Badge> findOne(Long id);

    /**
     * Delete the "id" badge.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
