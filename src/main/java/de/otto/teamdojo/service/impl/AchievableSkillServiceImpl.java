package de.otto.teamdojo.service.impl;

import de.otto.teamdojo.domain.Level;
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
import java.util.stream.Collectors;

@Service
@Transactional
public class AchievableSkillServiceImpl implements AchievableSkillService {

    private final Logger log = LoggerFactory.getLogger(AchievableSkillServiceImpl.class);

    private final SkillRepository skillRepository;

    private final TeamRepository teamRepository;


    public AchievableSkillServiceImpl(SkillRepository skillRepository, TeamRepository teamRepository) {
        this.skillRepository = skillRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public Page<AchievableSkillDTO> findAllByTeamId(Long teamId, List<Long> levelIds, Pageable pageable) {
        if (levelIds.isEmpty()) {
            addTeamRelatedLevels(teamId, levelIds);
        }
        return skillRepository.findAchievableSkill(teamId, levelIds, pageable);
    }

    private void addTeamRelatedLevels(Long teamId, List<Long> levelIds) {
        teamRepository.findById(teamId).ifPresent(team -> {
            List<Long> relatedLevelIds = team.getParticipations()
                .stream()
                .flatMap(dimension ->
                    dimension.getLevels().stream().map(Level::getId))
                .collect(Collectors.toList());
            levelIds.addAll(relatedLevelIds);
        });
    }
}
