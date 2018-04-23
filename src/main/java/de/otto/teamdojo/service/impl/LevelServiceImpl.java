package de.otto.teamdojo.service.impl;

import de.otto.teamdojo.service.LevelService;
import de.otto.teamdojo.domain.Level;
import de.otto.teamdojo.repository.LevelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import java.util.List;
/**
 * Service Implementation for managing Level.
 */
@Service
@Transactional
public class LevelServiceImpl implements LevelService {

    private final Logger log = LoggerFactory.getLogger(LevelServiceImpl.class);

    private final LevelRepository levelRepository;

    public LevelServiceImpl(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    /**
     * Save a level.
     *
     * @param level the entity to save
     * @return the persisted entity
     */
    @Override
    public Level save(Level level) {
        log.debug("Request to save Level : {}", level);
        return levelRepository.save(level);
    }

    /**
     * Get all the levels.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Level> findAll() {
        log.debug("Request to get all Levels");
        return levelRepository.findAll();
    }


    /**
     * Get one level by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Level> findOne(Long id) {
        log.debug("Request to get Level : {}", id);
        return levelRepository.findById(id);
    }

    /**
     * Delete the level by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Level : {}", id);
        levelRepository.deleteById(id);
    }
}
