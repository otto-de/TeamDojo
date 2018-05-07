package de.otto.teamdojo.service.impl;

import de.otto.teamdojo.domain.Badge;
import de.otto.teamdojo.domain.Level;
import de.otto.teamdojo.domain.Team;
import de.otto.teamdojo.repository.BadgeRepository;
import de.otto.teamdojo.repository.SkillRepository;
import de.otto.teamdojo.repository.TeamRepository;
import de.otto.teamdojo.service.AchievableSkillService;
import de.otto.teamdojo.service.TeamSkillService;
import de.otto.teamdojo.service.dto.AchievableSkillDTO;
import de.otto.teamdojo.service.dto.TeamSkillDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class AchievableSkillServiceImpl implements AchievableSkillService {

    private final Logger log = LoggerFactory.getLogger(AchievableSkillServiceImpl.class);

    private final SkillRepository skillRepository;

    private final TeamRepository teamRepository;

    private final BadgeRepository badgeRepository;

    private final TeamSkillService teamSkillService;


    public AchievableSkillServiceImpl(SkillRepository skillRepository,
                                      TeamRepository teamRepository,
                                      BadgeRepository badgeRepository, TeamSkillService teamSkillService) {
        this.skillRepository = skillRepository;
        this.teamRepository = teamRepository;
        this.badgeRepository = badgeRepository;
        this.teamSkillService = teamSkillService;
    }

    @Override
    public Page<AchievableSkillDTO> findAllByTeamAndLevelAndBadge(Long teamId, List<Long> levelIds, List<Long> badgeIds, Pageable pageable) {
        return levelIds.isEmpty() && badgeIds.isEmpty()
            ? findAllTeamRelated(teamId, pageable)
            : queryRepository(teamId, levelIds, badgeIds, pageable);
    }

    @Override
    public AchievableSkillDTO updateAchievableSkill(Long teamId, AchievableSkillDTO achievableSkill) {
        TeamSkillDTO teamSkill = new TeamSkillDTO();
        teamSkill.setTeamId(teamId);
        teamSkill.setSkillId(achievableSkill.getSkillId());
        teamSkill.setAchievedAt(achievableSkill.getAchievedAt());
        teamSkillService.save(teamSkill);
        return skillRepository.findAchievableSkill(teamId, achievableSkill.getSkillId());
    }


    private Page<AchievableSkillDTO> findAllTeamRelated(Long teamId, Pageable pageable) {
        Team team = getTeam(teamId);
        List<Long> relatedLevelIds = getTeamRelatedLevelIds(team);
        List<Long> relatedBadgeIds = getTeamRelatedBadgeIds(team);
        relatedBadgeIds.addAll(getDimensionlessBadgeIds());
        return queryRepository(teamId, relatedLevelIds, relatedBadgeIds, pageable);
    }

    private Team getTeam(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(NoSuchElementException::new);
    }

    private Page<AchievableSkillDTO> queryRepository(Long teamId, List<Long> levelIds, List<Long> badgeIds, Pageable pageable) {
        if (!levelIds.isEmpty() && !badgeIds.isEmpty()) {
            return skillRepository.findAchievableSkillsByLevelsAndBadges(teamId, levelIds, badgeIds, pageable);
        } else if (!levelIds.isEmpty()) {
            return skillRepository.findAchievableSkillsByLevels(teamId, levelIds, pageable);
        } else if (!badgeIds.isEmpty()) {
            return skillRepository.findAchievableSkillsByBadges(teamId, badgeIds, pageable);
        }
        return Page.empty();
    }

    private List<Long> getTeamRelatedLevelIds(Team team) {
        return team.getParticipations()
            .stream()
            .flatMap(dimension ->
                dimension.getLevels().stream().map(Level::getId))
            .collect(Collectors.toList());
    }

    private List<Long> getTeamRelatedBadgeIds(Team team) {
        return team.getParticipations()
            .stream()
            .flatMap(dimension ->
                dimension.getBadges().stream().map(Badge::getId))
            .distinct()
            .collect(Collectors.toList());
    }

    private List<Long> getDimensionlessBadgeIds() {
        return badgeRepository.findAllByDimensionsIsNull()
            .stream()
            .map(Badge::getId)
            .collect(Collectors.toList());
    }
}
