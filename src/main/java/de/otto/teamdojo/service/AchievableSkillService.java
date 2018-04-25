package de.otto.teamdojo.service;

import de.otto.teamdojo.service.dto.AchievableSkillDTO;

import java.util.List;

public interface AchievableSkillService {

    List<AchievableSkillDTO> findAllByTeamId(Long teamId);

}
