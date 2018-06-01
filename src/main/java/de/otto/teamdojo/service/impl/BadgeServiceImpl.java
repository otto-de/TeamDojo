package de.otto.teamdojo.service.impl;

import de.otto.teamdojo.domain.Badge;
import de.otto.teamdojo.repository.BadgeRepository;
import de.otto.teamdojo.service.ActivityService;
import de.otto.teamdojo.service.BadgeService;
import de.otto.teamdojo.service.dto.BadgeDTO;
import de.otto.teamdojo.service.mapper.BadgeMapper;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Badge.
 */
@Service
@Transactional
public class BadgeServiceImpl implements BadgeService {

    private final Logger log = LoggerFactory.getLogger(BadgeServiceImpl.class);

    private final BadgeRepository badgeRepository;

    private final BadgeMapper badgeMapper;

    private final ActivityService activityService;

    public BadgeServiceImpl(BadgeRepository badgeRepository, BadgeMapper badgeMapper, ActivityService activityService) {
        this.badgeRepository = badgeRepository;
        this.badgeMapper = badgeMapper;
        this.activityService = activityService;
    }

    /**
     * Save a badge.
     *
     * @param badgeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BadgeDTO save(BadgeDTO badgeDTO) throws JSONException {
        log.debug("Request to save Badge : {}", badgeDTO);
        boolean newBadge = badgeDTO.getId() == null;
        Badge badge = badgeMapper.toEntity(badgeDTO);
        badge = badgeRepository.save(badge);
        badgeDTO = badgeMapper.toDto(badge);
        if (newBadge) {
            this.activityService.createForNewBadge(badgeDTO);
        }
        return badgeDTO;
    }

    /**
     * Get all the badges.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BadgeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Badges");
        return badgeRepository.findAll(pageable)
            .map(badgeMapper::toDto);
    }

    public Page<BadgeDTO> findByIdIn(List<Long> badgeIds, Pageable pageable){
        log.debug("Request to get Badges by Badge Ids: {}", badgeIds);
        return badgeRepository.findByIdIn(badgeIds, pageable)
            .map(badgeMapper::toDto);

    }

    /**
     * Get all the Badge with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<BadgeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return badgeRepository.findAllWithEagerRelationships(pageable).map(badgeMapper::toDto);
    }


    /**
     * Get one badge by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BadgeDTO> findOne(Long id) {
        log.debug("Request to get Badge : {}", id);
        return badgeRepository.findOneWithEagerRelationships(id)
            .map(badgeMapper::toDto);
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
