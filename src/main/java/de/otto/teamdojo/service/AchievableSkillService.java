package de.otto.teamdojo.service;

import de.otto.teamdojo.service.dto.AchievableSkillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AchievableSkillService {

    Page<AchievableSkillDTO> findAllByTeamId(Long teamId, Pageable pageable);

}
