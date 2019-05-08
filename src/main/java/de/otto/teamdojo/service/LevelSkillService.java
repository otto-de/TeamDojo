package de.otto.teamdojo.service;

import de.otto.teamdojo.service.dto.LevelSkillDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing LevelSkill.
 */
public interface LevelSkillService {

    /**
     * Save a levelSkill.
     *
     * @param levelSkillDTO the entity to save
     * @return the persisted entity
     */
    LevelSkillDTO save(LevelSkillDTO levelSkillDTO);

    /**
     * Get all the levelSkills.
     *
     * @return the list of entities
     */
    List<LevelSkillDTO> findAll();


    /**
     * Get the "id" levelSkill.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LevelSkillDTO> findOne(Long id);


    List<LevelSkillDTO> findBySkillIdIn(List<Long> skillIds, Pageable pageable);

    /**
     * Delete the "id" levelSkill.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


}
