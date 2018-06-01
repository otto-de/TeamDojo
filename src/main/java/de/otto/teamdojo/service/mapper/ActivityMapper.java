package de.otto.teamdojo.service.mapper;

import de.otto.teamdojo.domain.Activity;
import de.otto.teamdojo.service.dto.ActivityDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Activity and its DTO ActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ActivityMapper extends EntityMapper<ActivityDTO, Activity> {



    default Activity fromId(Long id) {
        if (id == null) {
            return null;
        }
        Activity activity = new Activity();
        activity.setId(id);
        return activity;
    }
}
