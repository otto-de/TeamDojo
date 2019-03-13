package de.otto.teamdojo.service.impl;

import de.otto.teamdojo.service.LevelSkillService;
import de.otto.teamdojo.domain.LevelSkill;
import de.otto.teamdojo.repository.LevelSkillRepository;
import de.otto.teamdojo.service.dto.LevelSkillDTO;
import de.otto.teamdojo.service.mapper.LevelSkillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing LevelSkill.
 */
@Service
@Transactional
public class LevelSkillServiceImpl implements LevelSkillService {

    private final Logger log = LoggerFactory.getLogger(LevelSkillServiceImpl.class);

    private final LevelSkillRepository levelSkillRepository;

    private final LevelSkillMapper levelSkillMapper;

    public LevelSkillServiceImpl(LevelSkillRepository levelSkillRepository, LevelSkillMapper levelSkillMapper) {
        this.levelSkillRepository = levelSkillRepository;
        this.levelSkillMapper = levelSkillMapper;
    }

    /**
     * Save a levelSkill.
     *
     * @param levelSkillDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LevelSkillDTO save(LevelSkillDTO levelSkillDTO) {
        log.debug("Request to save LevelSkill : {}", levelSkillDTO);
        LevelSkill levelSkill = levelSkillMapper.toEntity(levelSkillDTO);
        levelSkill = levelSkillRepository.save(levelSkill);
        return levelSkillMapper.toDto(levelSkill);
    }

    /**
     * Get all the levelSkills.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LevelSkillDTO> findAll() {
        log.debug("Request to get all LevelSkills");
        return levelSkillRepository.findAll().stream()
            .map(levelSkillMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one levelSkill by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LevelSkillDTO> findOne(Long id) {
        log.debug("Request to get LevelSkill : {}", id);
        return levelSkillRepository.findById(id)
            .map(levelSkillMapper::toDto);
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
