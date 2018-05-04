package de.otto.teamdojo.service.mapper;

import de.otto.teamdojo.domain.Level;
import de.otto.teamdojo.service.dto.LevelDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Level and its DTO LevelDTO.
 */
@Mapper(componentModel = "spring", uses = {DimensionMapper.class})
public interface LevelMapper extends EntityMapper<LevelDTO, Level> {

    @Mapping(source = "dimension.id", target = "dimensionId")
    @Mapping(source = "dimension.name", target = "dimensionName")
    @Mapping(source = "dependsOn.id", target = "dependsOnId")
    @Mapping(source = "dependsOn.name", target = "dependsOnName")
    LevelDTO toDto(Level level);

    @Mapping(source = "dimensionId", target = "dimension")
    @Mapping(source = "dependsOnId", target = "dependsOn")
    @Mapping(target = "skills", ignore = true)
    Level toEntity(LevelDTO levelDTO);

    default Level fromId(Long id) {
        if (id == null) {
            return null;
        }
        Level level = new Level();
        level.setId(id);
        return level;
    }
}
