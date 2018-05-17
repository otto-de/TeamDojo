package de.otto.teamdojo.repository;

import de.otto.teamdojo.domain.BadgeSkill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the BadgeSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BadgeSkillRepository extends JpaRepository<BadgeSkill, Long>, JpaSpecificationExecutor<BadgeSkill> {

    Page<BadgeSkill> findBySkillIdIn(List<Long> skillsIds, Pageable pageable);

}
