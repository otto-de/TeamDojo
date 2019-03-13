package de.otto.teamdojo.service.impl;

import de.otto.teamdojo.service.DimensionService;
import de.otto.teamdojo.domain.Dimension;
import de.otto.teamdojo.repository.DimensionRepository;
import de.otto.teamdojo.service.dto.DimensionDTO;
import de.otto.teamdojo.service.mapper.DimensionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Dimension.
 */
@Service
@Transactional
public class DimensionServiceImpl implements DimensionService {

    private final Logger log = LoggerFactory.getLogger(DimensionServiceImpl.class);

    private final DimensionRepository dimensionRepository;

    private final DimensionMapper dimensionMapper;

    public DimensionServiceImpl(DimensionRepository dimensionRepository, DimensionMapper dimensionMapper) {
        this.dimensionRepository = dimensionRepository;
        this.dimensionMapper = dimensionMapper;
    }

    /**
     * Save a dimension.
     *
     * @param dimensionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DimensionDTO save(DimensionDTO dimensionDTO) {
        log.debug("Request to save Dimension : {}", dimensionDTO);
        Dimension dimension = dimensionMapper.toEntity(dimensionDTO);
        dimension = dimensionRepository.save(dimension);
        return dimensionMapper.toDto(dimension);
    }

    /**
     * Get all the dimensions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DimensionDTO> findAll() {
        log.debug("Request to get all Dimensions");
        return dimensionRepository.findAll().stream()
            .map(dimensionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one dimension by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DimensionDTO> findOne(Long id) {
        log.debug("Request to get Dimension : {}", id);
        return dimensionRepository.findById(id)
            .map(dimensionMapper::toDto);
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
