package de.otto.teamdojo.service;

import de.otto.teamdojo.domain.LevelSkill;
import de.otto.teamdojo.domain.LevelSkill_;
import de.otto.teamdojo.domain.Level_;
import de.otto.teamdojo.domain.Skill_;
import de.otto.teamdojo.repository.LevelSkillRepository;
import de.otto.teamdojo.service.dto.LevelSkillCriteria;
import de.otto.teamdojo.service.dto.LevelSkillDTO;
import de.otto.teamdojo.service.mapper.LevelSkillMapper;
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
 * Service for executing complex queries for LevelSkill entities in the database.
 * The main input is a {@link LevelSkillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LevelSkillDTO} or a {@link Page} of {@link LevelSkillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LevelSkillQueryService extends QueryService<LevelSkill> {

    private final Logger log = LoggerFactory.getLogger(LevelSkillQueryService.class);

    private final LevelSkillRepository levelSkillRepository;

    private final LevelSkillMapper levelSkillMapper;

    public LevelSkillQueryService(LevelSkillRepository levelSkillRepository, LevelSkillMapper levelSkillMapper) {
        this.levelSkillRepository = levelSkillRepository;
        this.levelSkillMapper = levelSkillMapper;
    }

    /**
     * Return a {@link List} of {@link LevelSkillDTO} which matches the criteria from the database
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LevelSkillDTO> findByCriteria(LevelSkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LevelSkill> specification = createSpecification(criteria);
        return levelSkillMapper.toDto(levelSkillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LevelSkillDTO} which matches the criteria from the database
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LevelSkillDTO> findByCriteria(LevelSkillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LevelSkill> specification = createSpecification(criteria);
        return levelSkillRepository.findAll(specification, page)
            .map(levelSkillMapper::toDto);
    }

    /**
     * Function to convert LevelSkillCriteria to a {@link Specification}
     */
    private Specification<LevelSkill> createSpecification(LevelSkillCriteria criteria) {
        Specification<LevelSkill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), LevelSkill_.id));
            }
            if (criteria.getSkillId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSkillId(), LevelSkill_.skill, Skill_.id));
            }
            if (criteria.getLevelId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getLevelId(), LevelSkill_.level, Level_.id));
            }
        }
        return specification;
    }

}
