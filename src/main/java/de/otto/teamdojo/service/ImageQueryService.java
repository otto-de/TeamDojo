package de.otto.teamdojo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import de.otto.teamdojo.domain.Image;
import de.otto.teamdojo.domain.*; // for static metamodels
import de.otto.teamdojo.repository.ImageRepository;
import de.otto.teamdojo.service.dto.ImageCriteria;

import de.otto.teamdojo.service.dto.ImageDTO;
import de.otto.teamdojo.service.mapper.ImageMapper;

/**
 * Service for executing complex queries for Image entities in the database.
 * The main input is a {@link ImageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ImageDTO} or a {@link Page} of {@link ImageDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ImageQueryService extends QueryService<Image> {

    private final Logger log = LoggerFactory.getLogger(ImageQueryService.class);

    private final ImageRepository imageRepository;

    private final ImageMapper imageMapper;

    public ImageQueryService(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    /**
     * Return a {@link List} of {@link ImageDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ImageDTO> findByCriteria(ImageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Image> specification = createSpecification(criteria);
        return imageMapper.toDto(imageRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ImageDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ImageDTO> findByCriteria(ImageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Image> specification = createSpecification(criteria);
        return imageRepository.findAll(specification, page)
            .map(imageMapper::toDto);
    }

    /**
     * Function to convert ImageCriteria to a {@link Specification}
     */
    private Specification<Image> createSpecification(ImageCriteria criteria) {
        Specification<Image> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Image_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Image_.name));
            }
        }
        return specification;
    }

}
