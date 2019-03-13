package de.otto.teamdojo.service.mapper;

import de.otto.teamdojo.domain.*;
import de.otto.teamdojo.service.dto.LevelSkillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LevelSkill and its DTO LevelSkillDTO.
 */
@Mapper(componentModel = "spring", uses = {SkillMapper.class, LevelMapper.class})
public interface LevelSkillMapper extends EntityMapper<LevelSkillDTO, LevelSkill> {

    @Mapping(source = "skill.id", target = "skillId")
    @Mapping(source = "skill.title", target = "skillTitle")
    @Mapping(source = "level.id", target = "levelId")
    @Mapping(source = "level.name", target = "levelName")
    LevelSkillDTO toDto(LevelSkill levelSkill);

    @Mapping(source = "skillId", target = "skill")
    @Mapping(source = "levelId", target = "level")
    LevelSkill toEntity(LevelSkillDTO levelSkillDTO);

    default LevelSkill fromId(Long id) {
        if (id == null) {
            return null;
        }
        LevelSkill levelSkill = new LevelSkill();
        levelSkill.setId(id);
        return levelSkill;
    }
}
