package de.otto.teamdojo.service;

import de.otto.teamdojo.domain.Skill_;
import de.otto.teamdojo.domain.TeamSkill_;
import de.otto.teamdojo.service.dto.AchievableSkillCriteria;
import de.otto.teamdojo.service.dto.AchievableSkillDTO;
import de.otto.teamdojo.service.dto.TeamCriteria;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AchievableSkillQueryService extends QueryService<AchievableSkillDTO> {

    private final Logger log = LoggerFactory.getLogger(AchievableSkillQueryService.class);


//    @Transactional(readOnly = true)
//    public List<AchievableSkillDTO> findByCriteria(TeamCriteria criteria) {
//        log.debug("find by criteria : {}", criteria);
//        final Specification<AchievableSkillDTO> specification = createSpecification(criteria);
//        return repository.findAll(specification);
//    }
//
//    @Transactional(readOnly = true)
//    public Page<AchievableSkillDTO> findByCriteria(TeamCriteria criteria, Pageable page) {
//        log.debug("find by criteria : {}, page: {}", criteria, page);
//        final Specification<AchievableSkillDTO> specification = createSpecification(criteria);
//        return repository.findAll(specification, page);
//    }
//
//
//    private Specification<AchievableSkillDTO> createSpecification(AchievableSkillCriteria criteria) {
//        Specification<AchievableSkillDTO> specification = Specification.where(null);
//        if (criteria != null) {
//            if (criteria.getTeamId() != null) {
//                if (criteria.getTeamId() != null) {
//                    // TODO
//                    specification = specification.and(buildReferringEntitySpecification(criteria.getTeamId(), Skill_.teams, TeamSkill_.id));
//                }
//            }
//
//        }
//        return specification;
//    }
}
