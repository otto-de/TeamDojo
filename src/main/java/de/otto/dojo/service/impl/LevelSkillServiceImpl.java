package de.otto.dojo.service.impl;

import de.otto.dojo.service.LevelSkillService;
import de.otto.dojo.domain.LevelSkill;
import de.otto.dojo.repository.LevelSkillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import java.util.List;
/**
 * Service Implementation for managing LevelSkill.
 */
@Service
@Transactional
public class LevelSkillServiceImpl implements LevelSkillService {

    private final Logger log = LoggerFactory.getLogger(LevelSkillServiceImpl.class);

    private final LevelSkillRepository levelSkillRepository;

    public LevelSkillServiceImpl(LevelSkillRepository levelSkillRepository) {
        this.levelSkillRepository = levelSkillRepository;
    }

    /**
     * Save a levelSkill.
     *
     * @param levelSkill the entity to save
     * @return the persisted entity
     */
    @Override
    public LevelSkill save(LevelSkill levelSkill) {
        log.debug("Request to save LevelSkill : {}", levelSkill);
        return levelSkillRepository.save(levelSkill);
    }

    /**
     * Get all the levelSkills.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LevelSkill> findAll() {
        log.debug("Request to get all LevelSkills");
        return levelSkillRepository.findAll();
    }


    /**
     * Get one levelSkill by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LevelSkill> findOne(Long id) {
        log.debug("Request to get LevelSkill : {}", id);
        return levelSkillRepository.findById(id);
    }

    /**
     * Delete the levelSkill by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LevelSkill : {}", id);
        levelSkillRepository.deleteById(id);
    }
}
