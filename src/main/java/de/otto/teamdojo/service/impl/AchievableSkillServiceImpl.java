package de.otto.teamdojo.service.impl;

import de.otto.teamdojo.domain.Badge;
import de.otto.teamdojo.domain.Level;
import de.otto.teamdojo.domain.Team;
import de.otto.teamdojo.repository.BadgeRepository;
import de.otto.teamdojo.repository.SkillRepository;
import de.otto.teamdojo.repository.TeamRepository;
import de.otto.teamdojo.service.AchievableSkillService;
import de.otto.teamdojo.service.dto.AchievableSkillDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AchievableSkillServiceImpl implements AchievableSkillService {

    private final Logger log = LoggerFactory.getLogger(AchievableSkillServiceImpl.class);

    private final SkillRepository skillRepository;

    private final TeamRepository teamRepository;

    private final BadgeRepository badgeRepository;


    public AchievableSkillServiceImpl(SkillRepository skillRepository,
                                      TeamRepository teamRepository,
                                      BadgeRepository badgeRepository) {
        this.skillRepository = skillRepository;
        this.teamRepository = teamRepository;
        this.badgeRepository = badgeRepository;
    }

    @Override
    public Page<AchievableSkillDTO> findAllByTeamId(Long teamId, List<Long> levelIds, List<Long> badgeIds, Pageable pageable) {
        if (levelIds.isEmpty() && badgeIds.isEmpty()) {
            teamRepository.findById(teamId).ifPresent(team -> {
                addTeamRelatedLevels(team, levelIds);
                addTeamRelatedBadges(team, badgeIds);
                addDimensionlessBadges(badgeIds);
            });
        }
        return skillRepository.findAchievableSkill(teamId, levelIds, badgeIds, pageable);
    }

    private void addTeamRelatedLevels(Team team, List<Long> levelIds) {
        Set<Long> relatedLevelIds = team.getParticipations()
            .stream()
            .flatMap(dimension ->
                dimension.getLevels().stream().map(Level::getId))
            .collect(Collectors.toSet());
        levelIds.addAll(relatedLevelIds);
    }

    private void addTeamRelatedBadges(Team team, List<Long> badgeIds) {
        Set<Long> relatedBadgeIds = team.getParticipations()
            .stream()
            .flatMap(dimension ->
                dimension.getBadges().stream().map(Badge::getId))
            .collect(Collectors.toSet());
        badgeIds.addAll(relatedBadgeIds);
    }

    private void addDimensionlessBadges(List<Long> badgeIds) {
        Set<Long> dimensionlessBadgeIds = badgeRepository.findAllByDimensionsIsNull()
            .stream()
            .map(Badge::getId)
            .collect(Collectors.toSet());
        badgeIds.addAll(dimensionlessBadgeIds);
    }
}
