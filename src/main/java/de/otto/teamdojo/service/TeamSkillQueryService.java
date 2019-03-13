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

import de.otto.teamdojo.domain.TeamSkill;
import de.otto.teamdojo.domain.*; // for static metamodels
import de.otto.teamdojo.repository.TeamSkillRepository;
import de.otto.teamdojo.service.dto.TeamSkillCriteria;

import de.otto.teamdojo.service.dto.TeamSkillDTO;
import de.otto.teamdojo.service.mapper.TeamSkillMapper;

/**
 * Service for executing complex queries for TeamSkill entities in the database.
 * The main input is a {@link TeamSkillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TeamSkillDTO} or a {@link Page} of {@link TeamSkillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TeamSkillQueryService extends QueryService<TeamSkill> {

    private final Logger log = LoggerFactory.getLogger(TeamSkillQueryService.class);

    private final TeamSkillRepository teamSkillRepository;

    private final TeamSkillMapper teamSkillMapper;

    public TeamSkillQueryService(TeamSkillRepository teamSkillRepository, TeamSkillMapper teamSkillMapper) {
        this.teamSkillRepository = teamSkillRepository;
        this.teamSkillMapper = teamSkillMapper;
    }

    /**
     * Return a {@link List} of {@link TeamSkillDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TeamSkillDTO> findByCriteria(TeamSkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TeamSkill> specification = createSpecification(criteria);
        return teamSkillMapper.toDto(teamSkillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TeamSkillDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TeamSkillDTO> findByCriteria(TeamSkillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TeamSkill> specification = createSpecification(criteria);
        return teamSkillRepository.findAll(specification, page)
            .map(teamSkillMapper::toDto);
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
            if (criteria.getCompletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompletedAt(), TeamSkill_.completedAt));
            }
            if (criteria.getVerifiedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVerifiedAt(), TeamSkill_.verifiedAt));
            }
            if (criteria.getIrrelevant() != null) {
                specification = specification.and(buildSpecification(criteria.getIrrelevant(), TeamSkill_.irrelevant));
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
