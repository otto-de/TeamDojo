package de.otto.teamdojo.service.mapper;

import de.otto.teamdojo.domain.BadgeSkill;
import de.otto.teamdojo.service.dto.BadgeSkillDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity BadgeSkill and its DTO BadgeSkillDTO.
 */
@Mapper(componentModel = "spring", uses = {BadgeMapper.class, SkillMapper.class})
public interface BadgeSkillMapper extends EntityMapper<BadgeSkillDTO, BadgeSkill> {

    @Mapping(source = "badge.id", target = "badgeId")
    @Mapping(source = "badge.name", target = "badgeName")
    @Mapping(source = "skill.id", target = "skillId")
    @Mapping(source = "skill.title", target = "skillTitle")
    BadgeSkillDTO toDto(BadgeSkill badgeSkill);

    @Mapping(source = "badgeId", target = "badge")
    @Mapping(source = "skillId", target = "skill")
    BadgeSkill toEntity(BadgeSkillDTO badgeSkillDTO);

    default BadgeSkill fromId(Long id) {
        if (id == null) {
            return null;
        }
        BadgeSkill badgeSkill = new BadgeSkill();
        badgeSkill.setId(id);
        return badgeSkill;
    }
}
