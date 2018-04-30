package de.otto.teamdojo.repository;

import de.otto.teamdojo.domain.Skill;
import de.otto.teamdojo.service.dto.AchievableSkillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Skill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>, JpaSpecificationExecutor<Skill> {

    @Query("SELECT" +
        " new de.otto.teamdojo.service.dto.AchievableSkillDTO(t.id, s.id, s.title, s.description, t.achievedAt)" +
        " FROM Skill s" +
        " LEFT JOIN s.teams t ON t.team.id = :teamId" +
        " JOIN s.levels l" +
        " WHERE l.level.id IN :levelIds")
    Page<AchievableSkillDTO> findAchievableSkill(@Param("teamId") Long teamId, @Param("levelIds") List<Long> levelIds, Pageable pageable);

}
