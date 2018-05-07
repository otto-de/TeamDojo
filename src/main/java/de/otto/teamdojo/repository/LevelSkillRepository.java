package de.otto.teamdojo.repository;

import de.otto.teamdojo.domain.LevelSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LevelSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LevelSkillRepository extends JpaRepository<LevelSkill, Long>, JpaSpecificationExecutor<LevelSkill> {

}
