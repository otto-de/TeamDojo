package de.otto.teamdojo.service.dto;

import io.github.jhipster.service.filter.*;

import java.io.Serializable;


/**
 * Criteria class for the Badge entity. This class is used in BadgeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /badges?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BadgeCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private InstantFilter availableUntil;

    private IntegerFilter availableAmount;

    private DoubleFilter multiplier;

    private DoubleFilter requiredScore;

    private LongFilter skillsId;

    private LongFilter dimensionsId;

    public BadgeCriteria() {
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public InstantFilter getAvailableUntil() {
        return availableUntil;
    }

    public void setAvailableUntil(InstantFilter availableUntil) {
        this.availableUntil = availableUntil;
    }

    public IntegerFilter getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(IntegerFilter availableAmount) {
        this.availableAmount = availableAmount;
    }

    public DoubleFilter getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(DoubleFilter multiplier) {
        this.multiplier = multiplier;
    }

    public DoubleFilter getRequiredScore() {
        return requiredScore;
    }

    public void setRequiredScore(DoubleFilter requiredScore) {
        this.requiredScore = requiredScore;
    }

    public LongFilter getSkillsId() {
        return skillsId;
    }

    public void setSkillsId(LongFilter skillsId) {
        this.skillsId = skillsId;
    }

    public LongFilter getDimensionsId() {
        return dimensionsId;
    }

    public void setDimensionsId(LongFilter dimensionsId) {
        this.dimensionsId = dimensionsId;
    }

    @Override
    public String toString() {
        return "BadgeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (availableUntil != null ? "availableUntil=" + availableUntil + ", " : "") +
            (availableAmount != null ? "availableAmount=" + availableAmount + ", " : "") +
            (multiplier != null ? "multiplier=" + multiplier + ", " : "") +
            (requiredScore != null ? "requiredScore=" + requiredScore + ", " : "") +
            (skillsId != null ? "skillsId=" + skillsId + ", " : "") +
            (dimensionsId != null ? "dimensionsId=" + dimensionsId + ", " : "") +
            "}";
    }

}
