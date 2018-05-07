package de.otto.teamdojo.repository;

import de.otto.teamdojo.domain.TeamSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TeamSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamSkillRepository extends JpaRepository<TeamSkill, Long>, JpaSpecificationExecutor<TeamSkill> {

}
