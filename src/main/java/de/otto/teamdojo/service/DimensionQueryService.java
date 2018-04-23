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

import de.otto.teamdojo.domain.Dimension;
import de.otto.teamdojo.domain.*; // for static metamodels
import de.otto.teamdojo.repository.DimensionRepository;
import de.otto.teamdojo.service.dto.DimensionCriteria;


/**
 * Service for executing complex queries for Dimension entities in the database.
 * The main input is a {@link DimensionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Dimension} or a {@link Page} of {@link Dimension} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DimensionQueryService extends QueryService<Dimension> {

    private final Logger log = LoggerFactory.getLogger(DimensionQueryService.class);

    private final DimensionRepository dimensionRepository;

    public DimensionQueryService(DimensionRepository dimensionRepository) {
        this.dimensionRepository = dimensionRepository;
    }

    /**
     * Return a {@link List} of {@link Dimension} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Dimension> findByCriteria(DimensionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Dimension> specification = createSpecification(criteria);
        return dimensionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Dimension} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Dimension> findByCriteria(DimensionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Dimension> specification = createSpecification(criteria);
        return dimensionRepository.findAll(specification, page);
    }

    /**
     * Function to convert DimensionCriteria to a {@link Specification}
     */
    private Specification<Dimension> createSpecification(DimensionCriteria criteria) {
        Specification<Dimension> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Dimension_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Dimension_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Dimension_.description));
            }
            if (criteria.getParticipantsId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getParticipantsId(), Dimension_.participants, Team_.id));
            }
        }
        return specification;
    }

}
