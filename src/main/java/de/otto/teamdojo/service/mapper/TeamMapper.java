package de.otto.teamdojo.service.mapper;

import de.otto.teamdojo.domain.Team;
import de.otto.teamdojo.service.dto.TeamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Team and its DTO TeamDTO.
 */
@Mapper(componentModel = "spring", uses = {DimensionMapper.class})
public interface TeamMapper extends EntityMapper<TeamDTO, Team> {


    @Mapping(target = "skills", ignore = true)
    Team toEntity(TeamDTO teamDTO);

    default Team fromId(Long id) {
        if (id == null) {
            return null;
        }
        Team team = new Team();
        team.setId(id);
        return team;
    }
}
