package de.otto.teamdojo.service.impl;

import de.otto.teamdojo.domain.Dimension;
import de.otto.teamdojo.repository.DimensionRepository;
import de.otto.teamdojo.service.DimensionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Dimension.
 */
@Service
@Transactional
public class DimensionServiceImpl implements DimensionService {

    private final Logger log = LoggerFactory.getLogger(DimensionServiceImpl.class);

    private final DimensionRepository dimensionRepository;

    public DimensionServiceImpl(DimensionRepository dimensionRepository) {
        this.dimensionRepository = dimensionRepository;
    }

    /**
     * Save a dimension.
     *
     * @param dimension the entity to save
     * @return the persisted entity
     */
    @Override
    public Dimension save(Dimension dimension) {
        log.debug("Request to save Dimension : {}", dimension);
        return dimensionRepository.save(dimension);
    }

    /**
     * Get all the dimensions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Dimension> findAll() {
        log.debug("Request to get all Dimensions");
        return dimensionRepository.findAll();
    }


    /**
     * Get one dimension by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Dimension> findOne(Long id) {
        log.debug("Request to get Dimension : {}", id);
        return dimensionRepository.findById(id);
    }

    /**
     * Delete the dimension by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dimension : {}", id);
        dimensionRepository.deleteById(id);
    }
}
