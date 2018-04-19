package de.otto.dojo.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




/**
 * Criteria class for the TeamSkill entity. This class is used in TeamSkillResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /team-skills?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TeamSkillCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private InstantFilter achievedAt;

    private BooleanFilter verified;

    private StringFilter note;

    public TeamSkillCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getAchievedAt() {
        return achievedAt;
    }

    public void setAchievedAt(InstantFilter achievedAt) {
        this.achievedAt = achievedAt;
    }

    public BooleanFilter getVerified() {
        return verified;
    }

    public void setVerified(BooleanFilter verified) {
        this.verified = verified;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "TeamSkillCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (achievedAt != null ? "achievedAt=" + achievedAt + ", " : "") +
                (verified != null ? "verified=" + verified + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
            "}";
    }

}
