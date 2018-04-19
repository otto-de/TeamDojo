package de.otto.dojo.repository;

import de.otto.dojo.domain.TeamSkill;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the TeamSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamSkillRepository extends JpaRepository<TeamSkill, Long>, JpaSpecificationExecutor<TeamSkill> {

}
