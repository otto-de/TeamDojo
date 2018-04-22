package de.otto.dojo.service.impl;

import de.otto.dojo.service.BadgeService;
import de.otto.dojo.domain.Badge;
import de.otto.dojo.repository.BadgeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Badge.
 */
@Service
@Transactional
public class BadgeServiceImpl implements BadgeService {

    private final Logger log = LoggerFactory.getLogger(BadgeServiceImpl.class);

    private final BadgeRepository badgeRepository;

    public BadgeServiceImpl(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    /**
     * Save a badge.
     *
     * @param badge the entity to save
     * @return the persisted entity
     */
    @Override
    public Badge save(Badge badge) {
        log.debug("Request to save Badge : {}", badge);
        return badgeRepository.save(badge);
    }

    /**
     * Get all the badges.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Badge> findAll(Pageable pageable) {
        log.debug("Request to get all Badges");
        return badgeRepository.findAll(pageable);
    }


    /**
     * Get one badge by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Badge> findOne(Long id) {
        log.debug("Request to get Badge : {}", id);
        return badgeRepository.findById(id);
    }

    /**
     * Delete the badge by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Badge : {}", id);
        badgeRepository.deleteById(id);
    }
}
