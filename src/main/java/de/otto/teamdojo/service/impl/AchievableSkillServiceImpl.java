package de.otto.teamdojo.service.impl;

import de.otto.teamdojo.repository.LevelRepository;
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

@Service
@Transactional
public class AchievableSkillServiceImpl implements AchievableSkillService {

    private final Logger log = LoggerFactory.getLogger(AchievableSkillServiceImpl.class);

    private final SkillRepository skillRepository;

    private final TeamRepository teamRepository;

    private final LevelRepository levelRepository;

    public AchievableSkillServiceImpl(SkillRepository skillRepository, TeamRepository teamRepository, LevelRepository levelRepository) {
        this.skillRepository = skillRepository;
        this.teamRepository = teamRepository;
        this.levelRepository = levelRepository;
    }

    @Override
    public Page<AchievableSkillDTO> findAllByTeamId(Long teamId, List<Long> levelIds, Pageable pageable) {
        if (levelIds.isEmpty()) {
            teamRepository.findById(teamId).ifPresent(team -> {

            });
        }
        return skillRepository.findAchievableSkill(teamId, levelIds, pageable);
    }
}
