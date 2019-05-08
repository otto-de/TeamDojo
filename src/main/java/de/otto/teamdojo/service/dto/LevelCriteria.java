package de.otto.teamdojo.service.dto;

import io.github.jhipster.service.filter.*;

import java.io.Serializable;


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

    private StringFilter description;

    private DoubleFilter requiredScore;

    private DoubleFilter instantMultiplier;

    private IntegerFilter completionBonus;

    private LongFilter dimensionId;

    private LongFilter dependsOnId;

    private LongFilter skillsId;

    private LongFilter imageId;

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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public DoubleFilter getRequiredScore() {
        return requiredScore;
    }

    public void setRequiredScore(DoubleFilter requiredScore) {
        this.requiredScore = requiredScore;
    }

    public DoubleFilter getInstantMultiplier() {
        return instantMultiplier;
    }

    public void setInstantMultiplier(DoubleFilter instantMultiplier) {
        this.instantMultiplier = instantMultiplier;
    }

    public IntegerFilter getCompletionBonus() {
        return completionBonus;
    }

    public void setCompletionBonus(IntegerFilter completionBonus) {
        this.completionBonus = completionBonus;
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

    public LongFilter getSkillsId() {
        return skillsId;
    }

    public void setSkillsId(LongFilter skillsId) {
        this.skillsId = skillsId;
    }

    public LongFilter getImageId() {
        return imageId;
    }

    public void setImageId(LongFilter imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "LevelCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (requiredScore != null ? "requiredScore=" + requiredScore + ", " : "") +
            (instantMultiplier != null ? "instantMultiplier=" + instantMultiplier + ", " : "") +
            (completionBonus != null ? "completionBonus=" + completionBonus + ", " : "") +
            (dimensionId != null ? "dimensionId=" + dimensionId + ", " : "") +
            (dependsOnId != null ? "dependsOnId=" + dependsOnId + ", " : "") +
            (skillsId != null ? "skillsId=" + skillsId + ", " : "") +
            (imageId != null ? "imageId=" + imageId + ", " : "") +
            "}";
    }

}
