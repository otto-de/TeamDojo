package de.otto.teamdojo.service.impl;

import de.otto.teamdojo.domain.Skill;
import de.otto.teamdojo.repository.SkillRepository;
import de.otto.teamdojo.service.SkillService;
import de.otto.teamdojo.service.dto.SkillDTO;
import de.otto.teamdojo.service.mapper.SkillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Skill.
 */
@Service
@Transactional
public class SkillServiceImpl implements SkillService {

    private final Logger log = LoggerFactory.getLogger(SkillServiceImpl.class);

    private final SkillRepository skillRepository;

    private final SkillMapper skillMapper;

    public SkillServiceImpl(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    /**
     * Save a skill.
     *
     * @param skillDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SkillDTO save(SkillDTO skillDTO) {
        log.debug("Request to save Skill : {}", skillDTO);
        Skill skill = skillMapper.toEntity(skillDTO);
        skill = skillRepository.save(skill);
        return skillMapper.toDto(skill);
    }

    /**
     * Get all the skills.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SkillDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Skills");
        return skillRepository.findAll(pageable)
            .map(skillMapper::toDto);
    }


    /**
     * Get one skill by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SkillDTO> findOne(Long id) {
        log.debug("Request to get Skill : {}", id);
        return skillRepository.findById(id)
            .map(skillMapper::toDto);
    }

    /**
     * Delete the skill by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Skill : {}", id);
        skillRepository.deleteById(id);
    }

    /**
     * Creates a new vote
     * @param id the entity to udpate
     * @param rateScore stars to update
     * @return the persisted entity
     */
    public SkillDTO createVote(Long id, Integer rateScore){
        Skill skill = skillRepository.findById(id).get();

        Integer rateCount = skill.getRateCount() == null ? 0 : skill.getRateCount();
        Double sumRate = (skill.getRateScore() == null ? 0 : skill.getRateScore()) * rateCount;
        Double newrate = sumRate + rateScore;
        Double avgRate = newrate / (rateCount + 1);

        skill.setRateScore(avgRate);
        skill.setRateCount(rateCount+1);
        skill = skillRepository.saveAndFlush(skill);

        return skillMapper.toDto(skill);
    }
}
