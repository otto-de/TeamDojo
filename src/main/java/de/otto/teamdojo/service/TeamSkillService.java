package de.otto.teamdojo.service;

import de.otto.teamdojo.service.dto.TeamSkillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TeamSkill.
 */
public interface TeamSkillService {

    /**
     * Save a teamSkill.
     *
     * @param teamSkillDTO the entity to save
     * @return the persisted entity
     */
    TeamSkillDTO save(TeamSkillDTO teamSkillDTO);

    /**
     * Get all the teamSkills.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TeamSkillDTO> findAll(Pageable pageable);


    /**
     * Get the "id" teamSkill.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TeamSkillDTO> findOne(Long id);

    /**
     * Delete the "id" teamSkill.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
