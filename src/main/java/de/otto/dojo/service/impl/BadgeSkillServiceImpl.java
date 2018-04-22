package de.otto.dojo.service.impl;

import de.otto.dojo.service.BadgeSkillService;
import de.otto.dojo.domain.BadgeSkill;
import de.otto.dojo.repository.BadgeSkillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing BadgeSkill.
 */
@Service
@Transactional
public class BadgeSkillServiceImpl implements BadgeSkillService {

    private final Logger log = LoggerFactory.getLogger(BadgeSkillServiceImpl.class);

    private final BadgeSkillRepository badgeSkillRepository;

    public BadgeSkillServiceImpl(BadgeSkillRepository badgeSkillRepository) {
        this.badgeSkillRepository = badgeSkillRepository;
    }

    /**
     * Save a badgeSkill.
     *
     * @param badgeSkill the entity to save
     * @return the persisted entity
     */
    @Override
    public BadgeSkill save(BadgeSkill badgeSkill) {
        log.debug("Request to save BadgeSkill : {}", badgeSkill);
        return badgeSkillRepository.save(badgeSkill);
    }

    /**
     * Get all the badgeSkills.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BadgeSkill> findAll(Pageable pageable) {
        log.debug("Request to get all BadgeSkills");
        return badgeSkillRepository.findAll(pageable);
    }


    /**
     * Get one badgeSkill by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BadgeSkill> findOne(Long id) {
        log.debug("Request to get BadgeSkill : {}", id);
        return badgeSkillRepository.findById(id);
    }

    /**
     * Delete the badgeSkill by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BadgeSkill : {}", id);
        badgeSkillRepository.deleteById(id);
    }
}
