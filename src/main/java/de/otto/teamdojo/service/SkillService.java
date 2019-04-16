package de.otto.teamdojo.service;

import de.otto.teamdojo.service.dto.SkillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Skill.
 */
public interface SkillService {

    /**
     * Save a skill.
     *
     * @param skillDTO the entity to save
     * @return the persisted entity
     */
    SkillDTO save(SkillDTO skillDTO);

    /**
     * Get all the skills.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SkillDTO> findAll(Pageable pageable);


    /**
     * Get the "id" skill.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SkillDTO> findOne(Long id);

    /**
     * Delete the "id" skill.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Creates a new vote
     *
     * @param id        the entity to udpate
     * @param rateScore stars to update
     * @return the persisted entity
     */
    SkillDTO createVote(Long id, Integer rateScore);
}
