package de.otto.teamdojo.service.impl;

import de.otto.teamdojo.repository.SkillRepository;
import de.otto.teamdojo.service.AchievableSkillService;
import de.otto.teamdojo.service.dto.AchievableSkillDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AchievableSkillServiceImpl implements AchievableSkillService {

    private final Logger log = LoggerFactory.getLogger(AchievableSkillServiceImpl.class);

    private final SkillRepository skillRepository;

    public AchievableSkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public Page<AchievableSkillDTO> findAllByTeamId(Long teamId, Pageable pageable) {
        return skillRepository.findAchievableSkill(teamId, pageable);
    }
}
