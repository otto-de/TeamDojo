package de.otto.teamdojo.repository;

import de.otto.teamdojo.domain.Skill;
import de.otto.teamdojo.domain.enumeration.SkillStatus;
import de.otto.teamdojo.service.dto.AchievableSkillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Spring Data JPA repository for the Skill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>, JpaSpecificationExecutor<Skill> {

    enum Filter {
        ALL() {
            @Override
            Page<AchievableSkillDTO> filter(Page<AchievableSkillDTO> page) {
                return page;
            }
        },
        COMPLETE() {
            @Override
            boolean allow(AchievableSkillDTO dto) {
                SkillStatus status = SkillStatus.determineSkillStatus(
                    null, dto.getAchievedAt(), dto.getSkillExpiryPeriod());
                return status == SkillStatus.ACHIEVED || status == SkillStatus.EXPIRING;
            }
        },
        INCOMPLETE() {
            @Override
            boolean allow(AchievableSkillDTO dto) {
                SkillStatus status = SkillStatus.determineSkillStatus(
                    null, dto.getAchievedAt(), dto.getSkillExpiryPeriod());
                return status == SkillStatus.OPEN || status == SkillStatus.EXPIRED;
            }
        },
        NONE() {
            @Override
            Page<AchievableSkillDTO> filter(Page<AchievableSkillDTO> page) {
                return new PageImpl<>(Collections.emptyList());
            }
        };

        static Filter getInstance(List<String> filter) {
            boolean complete = false;
            boolean incomplete = false;
            for (String s : filter) {
                if ("complete".equalsIgnoreCase(s)) {
                    complete = true;
                } else if ("incomplete".equalsIgnoreCase(s)) {
                    incomplete = true;
                }
            }
            return complete ? (incomplete ? ALL : COMPLETE) : (incomplete ? INCOMPLETE : NONE);
        }

        Page<AchievableSkillDTO> filter(Page<AchievableSkillDTO> page) {
            return new PageImpl<>(page.filter(this::allow).stream().collect(Collectors.toList()));
        }

        boolean allow(AchievableSkillDTO dto) {
            throw new UnsupportedOperationException();
        }

    }

    static Page<AchievableSkillDTO> filter(Page<AchievableSkillDTO> page, List<String> filter) {
        return Filter.getInstance(filter).filter(page);
    }

    default Page<AchievableSkillDTO> findAchievableSkillsByLevelsAndBadges(Long teamId, List<Long> levelIds,
                                                                           List<Long> badgeIds, List<String> filter,
                                                                           Pageable pageable) {
        return filter(findAchievableSkillsByLevelsAndBadges(teamId, levelIds, badgeIds, pageable), filter);
    }


    default Page<AchievableSkillDTO> findAchievableSkillsByLevels(Long teamId, List<Long> levelIds, List<String> filter,
                                                                  Pageable pageable) {
        return filter(findAchievableSkillsByLevels(teamId, levelIds, pageable), filter);
    }

    default Page<AchievableSkillDTO> findAchievableSkillsByBadges(Long teamId, List<Long> badgeIds, List<String> filter,
                                                                  Pageable pageable) {
        return filter(findAchievableSkillsByBadges(teamId, badgeIds, pageable), filter);
    }

    @Query("SELECT DISTINCT" +
        " new de.otto.teamdojo.service.dto.AchievableSkillDTO(t.id, s.id, s.title, s.description, t.completedAt, t.irrelevant, s.expiryPeriod, s.rateScore, s.rateCount)" +
        " FROM Skill s" +
        " LEFT JOIN s.teams t ON t.team.id = :teamId" +
        " LEFT JOIN s.levels l" +
        " LEFT JOIN s.badges b" +
        " WHERE (l.level.id IN :levelIds" +
        " OR b.badge.id IN :badgeIds)" +
        " ORDER BY s.title")
    Page<AchievableSkillDTO> findAchievableSkillsByLevelsAndBadges(
        @Param("teamId") Long teamId,
        @Param("levelIds") List<Long> levelIds,
        @Param("badgeIds") List<Long> badgeIds,
        Pageable pageable);


    @Query("SELECT DISTINCT" +
        " new de.otto.teamdojo.service.dto.AchievableSkillDTO(t.id, s.id, s.title, s.description, t.completedAt, t.irrelevant, s.expiryPeriod, s.rateScore, s.rateCount)" +
        " FROM Skill s" +
        " LEFT JOIN s.teams t ON t.team.id = :teamId" +
        " LEFT JOIN s.levels l" +
        " WHERE l.level.id IN :levelIds" +
        " ORDER BY s.title")
    Page<AchievableSkillDTO> findAchievableSkillsByLevels(
        @Param("teamId") Long teamId,
        @Param("levelIds") List<Long> levelIds,
        Pageable pageable);

    @Query("SELECT DISTINCT" +
        " new de.otto.teamdojo.service.dto.AchievableSkillDTO(t.id, s.id, s.title, s.description, t.completedAt, t.irrelevant, s.expiryPeriod, s.rateScore, s.rateCount)" +
        " FROM Skill s" +
        " LEFT JOIN s.teams t ON t.team.id = :teamId" +
        " LEFT JOIN s.badges b" +
        " WHERE b.badge.id IN :badgeIds" +
        " ORDER BY s.title")
    Page<AchievableSkillDTO> findAchievableSkillsByBadges(
        @Param("teamId") Long teamId,
        @Param("badgeIds") List<Long> badgeIds,
        Pageable pageable);


    @Query("SELECT DISTINCT" +
        " new de.otto.teamdojo.service.dto.AchievableSkillDTO(t.id, s.id, s.title, s.description, t.completedAt, t.irrelevant, s.expiryPeriod, s.rateScore, s.rateCount)" +
        " FROM Skill s" +
        " LEFT JOIN s.teams t ON t.team.id = :teamId" +
        " WHERE s.id = :skillId")
    AchievableSkillDTO findAchievableSkill(
        @Param("teamId") Long teamId,
        @Param("skillId") Long skillId);

}
