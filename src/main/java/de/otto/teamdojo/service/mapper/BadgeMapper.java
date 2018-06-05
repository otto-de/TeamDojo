package de.otto.teamdojo.service.mapper;

import de.otto.teamdojo.domain.*;
import de.otto.teamdojo.service.dto.BadgeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Badge and its DTO BadgeDTO.
 */
@Mapper(componentModel = "spring", uses = {DimensionMapper.class, ImageMapper.class})
public interface BadgeMapper extends EntityMapper<BadgeDTO, Badge> {

    @Mapping(source = "image.id", target = "imageId")
    BadgeDTO toDto(Badge badge);

    @Mapping(target = "skills", ignore = true)
    @Mapping(source = "imageId", target = "image")
    Badge toEntity(BadgeDTO badgeDTO);

    default Badge fromId(Long id) {
        if (id == null) {
            return null;
        }
        Badge badge = new Badge();
        badge.setId(id);
        return badge;
    }
}
