package de.otto.teamdojo.service.mapper;

import de.otto.teamdojo.domain.Organization;
import de.otto.teamdojo.service.dto.OrganizationDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Organization and its DTO OrganizationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrganizationMapper extends EntityMapper<OrganizationDTO, Organization> {


    default Organization fromId(Long id) {
        if (id == null) {
            return null;
        }
        Organization organization = new Organization();
        organization.setId(id);
        return organization;
    }
}
