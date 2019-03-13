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

import de.otto.teamdojo.domain.Team;
import de.otto.teamdojo.domain.*; // for static metamodels
import de.otto.teamdojo.repository.TeamRepository;
import de.otto.teamdojo.service.dto.TeamCriteria;

import de.otto.teamdojo.service.dto.TeamDTO;
import de.otto.teamdojo.service.mapper.TeamMapper;

/**
 * Service for executing complex queries for Team entities in the database.
 * The main input is a {@link TeamCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TeamDTO} or a {@link Page} of {@link TeamDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TeamQueryService extends QueryService<Team> {

    private final Logger log = LoggerFactory.getLogger(TeamQueryService.class);

    private final TeamRepository teamRepository;

    private final TeamMapper teamMapper;

    public TeamQueryService(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    /**
     * Return a {@link List} of {@link TeamDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TeamDTO> findByCriteria(TeamCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Team> specification = createSpecification(criteria);
        return teamMapper.toDto(teamRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TeamDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TeamDTO> findByCriteria(TeamCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Team> specification = createSpecification(criteria);
        return teamRepository.findAll(specification, page)
            .map(teamMapper::toDto);
    }

    /**
     * Function to convert TeamCriteria to a {@link Specification}
     */
    private Specification<Team> createSpecification(TeamCriteria criteria) {
        Specification<Team> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Team_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Team_.name));
            }
            if (criteria.getShortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortName(), Team_.shortName));
            }
            if (criteria.getSlogan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSlogan(), Team_.slogan));
            }
            if (criteria.getContactPerson() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactPerson(), Team_.contactPerson));
            }
            if (criteria.getParticipationsId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getParticipationsId(), Team_.participations, Dimension_.id));
            }
            if (criteria.getSkillsId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSkillsId(), Team_.skills, TeamSkill_.id));
            }
            if (criteria.getImageId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getImageId(), Team_.image, Image_.id));
            }
        }
        return specification;
    }

}
