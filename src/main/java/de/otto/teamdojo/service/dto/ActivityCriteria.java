package de.otto.teamdojo.service.dto;

import de.otto.teamdojo.domain.enumeration.ActivityType;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;


/**
 * Criteria class for the Activity entity. This class is used in ActivityResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /activities?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ActivityCriteria implements Serializable {
    private static final long serialVersionUID = 1L;
    private LongFilter id;
    private ActivityTypeFilter type;
    private StringFilter data;
    private InstantFilter createdAt;

    public ActivityCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ActivityTypeFilter getType() {
        return type;
    }

    public void setType(ActivityTypeFilter type) {
        this.type = type;
    }

    public StringFilter getData() {
        return data;
    }

    public void setData(StringFilter data) {
        this.data = data;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ActivityCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (data != null ? "data=" + data + ", " : "") +
            (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
            "}";
    }

    /**
     * Class for filtering ActivityType
     */
    public static class ActivityTypeFilter extends Filter<ActivityType> {
    }

}
