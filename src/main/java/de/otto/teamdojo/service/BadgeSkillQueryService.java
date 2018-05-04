package de.otto.teamdojo.service;

import de.otto.teamdojo.domain.BadgeSkill;
import de.otto.teamdojo.domain.BadgeSkill_;
import de.otto.teamdojo.domain.Badge_;
import de.otto.teamdojo.domain.Skill_;
import de.otto.teamdojo.repository.BadgeSkillRepository;
import de.otto.teamdojo.service.dto.BadgeSkillCriteria;
import de.otto.teamdojo.service.dto.BadgeSkillDTO;
import de.otto.teamdojo.service.mapper.BadgeSkillMapper;
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
 * Service for executing complex queries for BadgeSkill entities in the database.
 * The main input is a {@link BadgeSkillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BadgeSkillDTO} or a {@link Page} of {@link BadgeSkillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BadgeSkillQueryService extends QueryService<BadgeSkill> {

    private final Logger log = LoggerFactory.getLogger(BadgeSkillQueryService.class);

    private final BadgeSkillRepository badgeSkillRepository;

    private final BadgeSkillMapper badgeSkillMapper;

    public BadgeSkillQueryService(BadgeSkillRepository badgeSkillRepository, BadgeSkillMapper badgeSkillMapper) {
        this.badgeSkillRepository = badgeSkillRepository;
        this.badgeSkillMapper = badgeSkillMapper;
    }

    /**
     * Return a {@link List} of {@link BadgeSkillDTO} which matches the criteria from the database
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BadgeSkillDTO> findByCriteria(BadgeSkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BadgeSkill> specification = createSpecification(criteria);
        return badgeSkillMapper.toDto(badgeSkillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BadgeSkillDTO} which matches the criteria from the database
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BadgeSkillDTO> findByCriteria(BadgeSkillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BadgeSkill> specification = createSpecification(criteria);
        return badgeSkillRepository.findAll(specification, page)
            .map(badgeSkillMapper::toDto);
    }

    /**
     * Function to convert BadgeSkillCriteria to a {@link Specification}
     */
    private Specification<BadgeSkill> createSpecification(BadgeSkillCriteria criteria) {
        Specification<BadgeSkill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BadgeSkill_.id));
            }
            if (criteria.getScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore(), BadgeSkill_.score));
            }
            if (criteria.getBadgeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getBadgeId(), BadgeSkill_.badge, Badge_.id));
            }
            if (criteria.getSkillId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSkillId(), BadgeSkill_.skill, Skill_.id));
            }
        }
        return specification;
    }

}
