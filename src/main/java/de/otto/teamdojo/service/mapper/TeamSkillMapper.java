package de.otto.teamdojo.service.mapper;

import de.otto.teamdojo.domain.TeamSkill;
import de.otto.teamdojo.service.dto.TeamSkillDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity TeamSkill and its DTO TeamSkillDTO.
 */
@Mapper(componentModel = "spring", uses = {SkillMapper.class, TeamMapper.class})
public interface TeamSkillMapper extends EntityMapper<TeamSkillDTO, TeamSkill> {

    @Mapping(source = "skill.id", target = "skillId")
    @Mapping(source = "skill.title", target = "skillTitle")
    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "team.name", target = "teamName")
    TeamSkillDTO toDto(TeamSkill teamSkill);

    @Mapping(source = "skillId", target = "skill")
    @Mapping(source = "teamId", target = "team")
    TeamSkill toEntity(TeamSkillDTO teamSkillDTO);

    default TeamSkill fromId(Long id) {
        if (id == null) {
            return null;
        }
        TeamSkill teamSkill = new TeamSkill();
        teamSkill.setId(id);
        return teamSkill;
    }
}
