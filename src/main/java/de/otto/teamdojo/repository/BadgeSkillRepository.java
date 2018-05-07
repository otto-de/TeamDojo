package de.otto.teamdojo.repository;

import de.otto.teamdojo.domain.BadgeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BadgeSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BadgeSkillRepository extends JpaRepository<BadgeSkill, Long>, JpaSpecificationExecutor<BadgeSkill> {

}
