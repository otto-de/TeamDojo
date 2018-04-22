package de.otto.dojo.repository;

import de.otto.dojo.domain.BadgeSkill;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the BadgeSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BadgeSkillRepository extends JpaRepository<BadgeSkill, Long>, JpaSpecificationExecutor<BadgeSkill> {

}
