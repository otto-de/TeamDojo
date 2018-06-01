package de.otto.teamdojo.service.mapper;

import de.otto.teamdojo.domain.*;
import de.otto.teamdojo.service.dto.ReportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Report and its DTO ReportDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReportMapper extends EntityMapper<ReportDTO, Report> {



    default Report fromId(Long id) {
        if (id == null) {
            return null;
        }
        Report report = new Report();
        report.setId(id);
        return report;
    }
}
