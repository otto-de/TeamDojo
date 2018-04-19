package de.otto.dojo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import de.otto.dojo.domain.Badge;
import de.otto.dojo.domain.*; // for static metamodels
import de.otto.dojo.repository.BadgeRepository;
import de.otto.dojo.service.dto.BadgeCriteria;


/**
 * Service for executing complex queries for Badge entities in the database.
 * The main input is a {@link BadgeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Badge} or a {@link Page} of {@link Badge} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BadgeQueryService extends QueryService<Badge> {

    private final Logger log = LoggerFactory.getLogger(BadgeQueryService.class);

    private final BadgeRepository badgeRepository;

    public BadgeQueryService(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    /**
     * Return a {@link List} of {@link Badge} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Badge> findByCriteria(BadgeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Badge> specification = createSpecification(criteria);
        return badgeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Badge} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Badge> findByCriteria(BadgeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Badge> specification = createSpecification(criteria);
        return badgeRepository.findAll(specification, page);
    }

    /**
     * Function to convert BadgeCriteria to a {@link Specification}
     */
    private Specification<Badge> createSpecification(BadgeCriteria criteria) {
        Specification<Badge> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Badge_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Badge_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Badge_.description));
            }
            if (criteria.getAvailableUntil() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAvailableUntil(), Badge_.availableUntil));
            }
            if (criteria.getAvailableAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAvailableAmount(), Badge_.availableAmount));
            }
            if (criteria.getRequiredScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequiredScore(), Badge_.requiredScore));
            }
        }
        return specification;
    }

}
