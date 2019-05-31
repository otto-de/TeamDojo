package de.otto.teamdojo.service;

import de.otto.teamdojo.domain.Activity;
import de.otto.teamdojo.domain.Activity_;
import de.otto.teamdojo.repository.ActivityRepository;
import de.otto.teamdojo.service.dto.ActivityCriteria;
import de.otto.teamdojo.service.dto.ActivityDTO;
import de.otto.teamdojo.service.mapper.ActivityMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for Activity entities in the database.
 * The main input is a {@link ActivityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ActivityDTO} or a {@link Page} of {@link ActivityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ActivityQueryService extends QueryService<Activity> {

    private final Logger log = LoggerFactory.getLogger(ActivityQueryService.class);

    private final ActivityRepository activityRepository;

    private final ActivityMapper activityMapper;

    public ActivityQueryService(ActivityRepository activityRepository, ActivityMapper activityMapper) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
    }

    /**
     * Return a {@link List} of {@link ActivityDTO} which matches the criteria from the database
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ActivityDTO> findByCriteria(ActivityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Activity> specification = createSpecification(criteria);
        return activityMapper.toDto(activityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ActivityDTO} which matches the criteria from the database
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ActivityDTO> findByCriteria(ActivityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Activity> specification = createSpecification(criteria);
        return activityRepository.findAll(specification, page)
            .map(activityMapper::toDto);
    }

    /**
     * Function to convert ActivityCriteria to a {@link Specification}
     */
    private Specification<Activity> createSpecification(ActivityCriteria criteria) {
        Specification<Activity> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Activity_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Activity_.type));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildStringSpecification(criteria.getData(), Activity_.data));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Activity_.createdAt));
            }
        }
        return specification;
    }

}
