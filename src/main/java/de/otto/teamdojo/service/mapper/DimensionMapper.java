package de.otto.teamdojo.service.mapper;

import de.otto.teamdojo.domain.Dimension;
import de.otto.teamdojo.service.dto.DimensionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Dimension and its DTO DimensionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DimensionMapper extends EntityMapper<DimensionDTO, Dimension> {


    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "levels", ignore = true)
    @Mapping(target = "badges", ignore = true)
    Dimension toEntity(DimensionDTO dimensionDTO);

    default Dimension fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dimension dimension = new Dimension();
        dimension.setId(id);
        return dimension;
    }
}
