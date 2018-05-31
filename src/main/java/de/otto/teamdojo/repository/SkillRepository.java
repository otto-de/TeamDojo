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

    @Query("SELECT DISTINCT" +
        " new de.otto.teamdojo.service.dto.AchievableSkillDTO(t.id, s.id, s.title, s.description, t.completedAt, t.irrelevant, s.rateScore, s.rateCount)" +
        " FROM Skill s" +
        " LEFT JOIN s.teams t ON t.team.id = :teamId" +
        " LEFT JOIN s.levels l" +
        " LEFT JOIN s.badges b" +
        " WHERE (l.level.id IN :levelIds" +
        " OR b.badge.id IN :badgeIds)" +
        " AND (" +
        "  ( ('COMPLETE' IN :filter) AND (t.completedAt is not null) )" +
        "   OR ( ('INCOMPLETE' IN :filter) AND (t.completedAt is null) )" +
        " )" +
        " ORDER BY s.title")
    Page<AchievableSkillDTO> findAchievableSkillsByLevelsAndBadges(
        @Param("teamId") Long teamId,
        @Param("levelIds") List<Long> levelIds,
        @Param("badgeIds") List<Long> badgeIds,
        @Param("filter") List<String> filter,
        Pageable pageable);


    @Query("SELECT DISTINCT" +
        " new de.otto.teamdojo.service.dto.AchievableSkillDTO(t.id, s.id, s.title, s.description, t.completedAt, t.irrelevant, s.rateScore, s.rateCount)" +
        " FROM Skill s" +
        " LEFT JOIN s.teams t ON t.team.id = :teamId" +
        " LEFT JOIN s.levels l" +
        " WHERE l.level.id IN :levelIds" +
        " AND (" +
        "  ( ('COMPLETE' IN :filter)  AND (t.completedAt is not null) )" +
        "   OR ( ('INCOMPLETE' IN :filter) AND (t.completedAt is null) )" +
        " )" +
        " ORDER BY s.title")
    Page<AchievableSkillDTO> findAchievableSkillsByLevels(
        @Param("teamId") Long teamId,
        @Param("levelIds") List<Long> levelIds,
        @Param("filter") List<String> filter,
        Pageable pageable);

    @Query("SELECT DISTINCT" +
        " new de.otto.teamdojo.service.dto.AchievableSkillDTO(t.id, s.id, s.title, s.description, t.completedAt, t.irrelevant, s.rateScore, s.rateCount)" +
        " FROM Skill s" +
        " LEFT JOIN s.teams t ON t.team.id = :teamId" +
        " LEFT JOIN s.badges b" +
        " WHERE b.badge.id IN :badgeIds" +
        " AND (" +
        "  ( ('COMPLETE' IN :filter) AND (t.completedAt is not null) )" +
        "   OR ( ('INCOMPLETE' IN :filter) AND (t.completedAt is null) )" +
        " )" +
        " ORDER BY s.title")
    Page<AchievableSkillDTO> findAchievableSkillsByBadges(
        @Param("teamId") Long teamId,
        @Param("badgeIds") List<Long> badgeIds,
        @Param("filter") List<String> filter,
        Pageable pageable);


    @Query("SELECT DISTINCT" +
        " new de.otto.teamdojo.service.dto.AchievableSkillDTO(t.id, s.id, s.title, s.description, t.completedAt, t.irrelevant, s.rateScore, s.rateCount)" +
        " FROM Skill s" +
        " LEFT JOIN s.teams t ON t.team.id = :teamId" +
        " WHERE s.id = :skillId")
    AchievableSkillDTO findAchievableSkill(
        @Param("teamId") Long teamId,
        @Param("skillId") Long skillId);
}
