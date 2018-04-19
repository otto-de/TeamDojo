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

import de.otto.dojo.domain.TeamSkill;
import de.otto.dojo.domain.*; // for static metamodels
import de.otto.dojo.repository.TeamSkillRepository;
import de.otto.dojo.service.dto.TeamSkillCriteria;


/**
 * Service for executing complex queries for TeamSkill entities in the database.
 * The main input is a {@link TeamSkillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TeamSkill} or a {@link Page} of {@link TeamSkill} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TeamSkillQueryService extends QueryService<TeamSkill> {

    private final Logger log = LoggerFactory.getLogger(TeamSkillQueryService.class);

    private final TeamSkillRepository teamSkillRepository;

    public TeamSkillQueryService(TeamSkillRepository teamSkillRepository) {
        this.teamSkillRepository = teamSkillRepository;
    }

    /**
     * Return a {@link List} of {@link TeamSkill} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TeamSkill> findByCriteria(TeamSkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TeamSkill> specification = createSpecification(criteria);
        return teamSkillRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TeamSkill} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TeamSkill> findByCriteria(TeamSkillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TeamSkill> specification = createSpecification(criteria);
        return teamSkillRepository.findAll(specification, page);
    }

    /**
     * Function to convert TeamSkillCriteria to a {@link Specification}
     */
    private Specification<TeamSkill> createSpecification(TeamSkillCriteria criteria) {
        Specification<TeamSkill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TeamSkill_.id));
            }
            if (criteria.getAchievedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAchievedAt(), TeamSkill_.achievedAt));
            }
            if (criteria.getVerified() != null) {
                specification = specification.and(buildSpecification(criteria.getVerified(), TeamSkill_.verified));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), TeamSkill_.note));
            }
            if (criteria.getSkillId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSkillId(), TeamSkill_.skill, Skill_.id));
            }
            if (criteria.getTeamId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTeamId(), TeamSkill_.team, Team_.id));
            }
        }
        return specification;
    }

}
