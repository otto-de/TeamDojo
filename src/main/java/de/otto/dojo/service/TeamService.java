package de.otto.dojo.service;

import de.otto.dojo.domain.Team;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Team.
 */
public interface TeamService {

    /**
     * Save a team.
     *
     * @param team the entity to save
     * @return the persisted entity
     */
    Team save(Team team);

    /**
     * Get all the teams.
     *
     * @return the list of entities
     */
    List<Team> findAll();


    /**
     * Get the "id" team.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Team> findOne(Long id);

    /**
     * Delete the "id" team.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
