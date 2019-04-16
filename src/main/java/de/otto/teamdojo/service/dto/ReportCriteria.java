package de.otto.teamdojo.service.dto;

import de.otto.teamdojo.domain.enumeration.ReportType;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;


/**
 * Criteria class for the Report entity. This class is used in ReportResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /reports?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReportCriteria implements Serializable {
    private static final long serialVersionUID = 1L;
    private LongFilter id;
    private StringFilter title;
    private StringFilter description;
    private ReportTypeFilter type;
    private InstantFilter creationDate;

    public ReportCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public ReportTypeFilter getType() {
        return type;
    }

    public void setType(ReportTypeFilter type) {
        this.type = type;
    }

    public InstantFilter getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(InstantFilter creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "ReportCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (creationDate != null ? "creationDate=" + creationDate + ", " : "") +
            "}";
    }

    /**
     * Class for filtering ReportType
     */
    public static class ReportTypeFilter extends Filter<ReportType> {
    }

}
