package de.otto.dojo.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Level entity. This class is used in LevelResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /levels?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LevelCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private FloatFilter requiredScore;

    private LongFilter dimensionId;

    private LongFilter dependsOnId;

    public LevelCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public FloatFilter getRequiredScore() {
        return requiredScore;
    }

    public void setRequiredScore(FloatFilter requiredScore) {
        this.requiredScore = requiredScore;
    }

    public LongFilter getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(LongFilter dimensionId) {
        this.dimensionId = dimensionId;
    }

    public LongFilter getDependsOnId() {
        return dependsOnId;
    }

    public void setDependsOnId(LongFilter dependsOnId) {
        this.dependsOnId = dependsOnId;
    }

    @Override
    public String toString() {
        return "LevelCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (requiredScore != null ? "requiredScore=" + requiredScore + ", " : "") +
                (dimensionId != null ? "dimensionId=" + dimensionId + ", " : "") +
                (dependsOnId != null ? "dependsOnId=" + dependsOnId + ", " : "") +
            "}";
    }

}
