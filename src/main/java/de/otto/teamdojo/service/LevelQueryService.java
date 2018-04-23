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

import de.otto.teamdojo.domain.Level;
import de.otto.teamdojo.domain.*; // for static metamodels
import de.otto.teamdojo.repository.LevelRepository;
import de.otto.teamdojo.service.dto.LevelCriteria;


/**
 * Service for executing complex queries for Level entities in the database.
 * The main input is a {@link LevelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Level} or a {@link Page} of {@link Level} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LevelQueryService extends QueryService<Level> {

    private final Logger log = LoggerFactory.getLogger(LevelQueryService.class);

    private final LevelRepository levelRepository;

    public LevelQueryService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    /**
     * Return a {@link List} of {@link Level} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Level> findByCriteria(LevelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Level> specification = createSpecification(criteria);
        return levelRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Level} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Level> findByCriteria(LevelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Level> specification = createSpecification(criteria);
        return levelRepository.findAll(specification, page);
    }

    /**
     * Function to convert LevelCriteria to a {@link Specification}
     */
    private Specification<Level> createSpecification(LevelCriteria criteria) {
        Specification<Level> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Level_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Level_.name));
            }
            if (criteria.getRequiredScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequiredScore(), Level_.requiredScore));
            }
            if (criteria.getDimensionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getDimensionId(), Level_.dimension, Dimension_.id));
            }
            if (criteria.getDependsOnId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getDependsOnId(), Level_.dependsOn, Level_.id));
            }
            if (criteria.getSkillsId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSkillsId(), Level_.skills, LevelSkill_.id));
            }
        }
        return specification;
    }

}
