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
 * Criteria class for the Skill entity. This class is used in SkillResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /skills?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SkillCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter title;

    private StringFilter description;

    private StringFilter validation;

    private StringFilter expiryPeriod;

    private LongFilter teamsId;

    private LongFilter badgesId;

    private LongFilter levelsId;

    public SkillCriteria() {
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

    public StringFilter getValidation() {
        return validation;
    }

    public void setValidation(StringFilter validation) {
        this.validation = validation;
    }

    public StringFilter getExpiryPeriod() {
        return expiryPeriod;
    }

    public void setExpiryPeriod(StringFilter expiryPeriod) {
        this.expiryPeriod = expiryPeriod;
    }

    public LongFilter getTeamsId() {
        return teamsId;
    }

    public void setTeamsId(LongFilter teamsId) {
        this.teamsId = teamsId;
    }

    public LongFilter getBadgesId() {
        return badgesId;
    }

    public void setBadgesId(LongFilter badgesId) {
        this.badgesId = badgesId;
    }

    public LongFilter getLevelsId() {
        return levelsId;
    }

    public void setLevelsId(LongFilter levelsId) {
        this.levelsId = levelsId;
    }

    @Override
    public String toString() {
        return "SkillCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (validation != null ? "validation=" + validation + ", " : "") +
                (expiryPeriod != null ? "expiryPeriod=" + expiryPeriod + ", " : "") +
                (teamsId != null ? "teamsId=" + teamsId + ", " : "") +
                (badgesId != null ? "badgesId=" + badgesId + ", " : "") +
                (levelsId != null ? "levelsId=" + levelsId + ", " : "") +
            "}";
    }

}
