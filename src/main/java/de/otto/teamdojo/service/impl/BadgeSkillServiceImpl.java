package de.otto.teamdojo.service.impl;

import de.otto.teamdojo.domain.BadgeSkill;
import de.otto.teamdojo.repository.BadgeSkillRepository;
import de.otto.teamdojo.service.BadgeSkillService;
import de.otto.teamdojo.service.dto.BadgeSkillDTO;
import de.otto.teamdojo.service.mapper.BadgeSkillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing BadgeSkill.
 */
@Service
@Transactional
public class BadgeSkillServiceImpl implements BadgeSkillService {

    private final Logger log = LoggerFactory.getLogger(BadgeSkillServiceImpl.class);

    private final BadgeSkillRepository badgeSkillRepository;

    private final BadgeSkillMapper badgeSkillMapper;

    public BadgeSkillServiceImpl(BadgeSkillRepository badgeSkillRepository, BadgeSkillMapper badgeSkillMapper) {
        this.badgeSkillRepository = badgeSkillRepository;
        this.badgeSkillMapper = badgeSkillMapper;
    }

    /**
     * Save a badgeSkill.
     *
     * @param badgeSkillDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BadgeSkillDTO save(BadgeSkillDTO badgeSkillDTO) {
        log.debug("Request to save BadgeSkill : {}", badgeSkillDTO);
        BadgeSkill badgeSkill = badgeSkillMapper.toEntity(badgeSkillDTO);
        badgeSkill = badgeSkillRepository.save(badgeSkill);
        return badgeSkillMapper.toDto(badgeSkill);
    }

    /**
     * Get all the badgeSkills.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BadgeSkillDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BadgeSkills");
        return badgeSkillRepository.findAll(pageable)
            .map(badgeSkillMapper::toDto);
    }


    /**
     * Get one badgeSkill by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BadgeSkillDTO> findOne(Long id) {
        log.debug("Request to get BadgeSkill : {}", id);
        return badgeSkillRepository.findById(id)
            .map(badgeSkillMapper::toDto);
    }

    /**
     * Get one badgeSkill by skill id.
     *
     * @param skillIds the id of the skills
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public List<BadgeSkillDTO> findBySkillIdIn(List<Long> skillIds, Pageable pageable) {
        log.debug("Request to get BadgeSkill by skill Ids: {}", skillIds);

        return badgeSkillRepository.findBySkillIdIn(skillIds, pageable)
            .stream()
            .map(badgeSkillMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
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
