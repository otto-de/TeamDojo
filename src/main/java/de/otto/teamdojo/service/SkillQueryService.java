package de.otto.teamdojo.service;

import de.otto.teamdojo.domain.*;
import de.otto.teamdojo.repository.SkillRepository;
import de.otto.teamdojo.service.dto.SkillCriteria;
import de.otto.teamdojo.service.dto.SkillDTO;
import de.otto.teamdojo.service.mapper.SkillMapper;
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
 * Service for executing complex queries for Skill entities in the database.
 * The main input is a {@link SkillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SkillDTO} or a {@link Page} of {@link SkillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SkillQueryService extends QueryService<Skill> {

    private final Logger log = LoggerFactory.getLogger(SkillQueryService.class);

    private final SkillRepository skillRepository;

    private final SkillMapper skillMapper;

    public SkillQueryService(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    /**
     * Return a {@link List} of {@link SkillDTO} which matches the criteria from the database
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SkillDTO> findByCriteria(SkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Skill> specification = createSpecification(criteria);
        return skillMapper.toDto(skillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SkillDTO} which matches the criteria from the database
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SkillDTO> findByCriteria(SkillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Skill> specification = createSpecification(criteria);
        return skillRepository.findAll(specification, page)
            .map(skillMapper::toDto);
    }

    /**
     * Function to convert SkillCriteria to a {@link Specification}
     */
    private Specification<Skill> createSpecification(SkillCriteria criteria) {
        Specification<Skill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Skill_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Skill_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Skill_.description));
            }
            if (criteria.getImplementation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImplementation(), Skill_.implementation));
            }
            if (criteria.getValidation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValidation(), Skill_.validation));
            }
            if (criteria.getExpiryPeriod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExpiryPeriod(), Skill_.expiryPeriod));
            }
            if (criteria.getTeamsId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTeamsId(), Skill_.teams, TeamSkill_.id));
            }
            if (criteria.getBadgesId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getBadgesId(), Skill_.badges, BadgeSkill_.id));
            }
            if (criteria.getLevelsId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getLevelsId(), Skill_.levels, LevelSkill_.id));
            }
        }
        return specification;
    }

}
