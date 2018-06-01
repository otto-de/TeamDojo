package de.otto.teamdojo.service;

import de.otto.teamdojo.service.dto.BadgeDTO;
import org.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Badge.
 */
public interface BadgeService {

    /**
     * Save a badge.
     *
     * @param badgeDTO the entity to save
     * @return the persisted entity
     */
    BadgeDTO save(BadgeDTO badgeDTO) throws JSONException;

    /**
     * Get all the badges.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BadgeDTO> findAll(Pageable pageable);

    Page<BadgeDTO> findByIdIn(List<Long> badgeIds, Pageable pageable);

    /**
     * Get all the Badge with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<BadgeDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" badge.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BadgeDTO> findOne(Long id);

    /**
     * Delete the "id" badge.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
