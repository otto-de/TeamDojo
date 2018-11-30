package de.otto.teamdojo.service.dto;

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

    private InstantFilter completedAt;

    private InstantFilter verifiedAt;

    private BooleanFilter irrelevant;

    private StringFilter note;

    private IntegerFilter vote;

    private StringFilter voters;

    private LongFilter skillId;

    private LongFilter teamId;

    public TeamSkillCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(InstantFilter completedAt) {
        this.completedAt = completedAt;
    }

    public InstantFilter getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(InstantFilter verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public BooleanFilter getIrrelevant() {
        return irrelevant;
    }

    public void setIrrelevant(BooleanFilter irrelevant) {
        this.irrelevant = irrelevant;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public IntegerFilter getVote() {
        return vote;
    }

    public void setVote(IntegerFilter vote) {
        this.vote = vote;
    }

    public StringFilter getVoters() {
        return voters;
    }

    public void setVoters(StringFilter voters) {
        this.voters = voters;
    }

    public LongFilter getSkillId() {
        return skillId;
    }

    public void setSkillId(LongFilter skillId) {
        this.skillId = skillId;
    }

    public LongFilter getTeamId() {
        return teamId;
    }

    public void setTeamId(LongFilter teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "TeamSkillCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (completedAt != null ? "completedAt=" + completedAt + ", " : "") +
                (verifiedAt != null ? "verifiedAt=" + verifiedAt + ", " : "") +
                (irrelevant != null ? "irrelevant=" + irrelevant + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (vote != null ? "vote=" + vote + ", " : "") +
                (voters != null ? "voters=" + voters + ", " : "") +
                (skillId != null ? "skillId=" + skillId + ", " : "") +
                (teamId != null ? "teamId=" + teamId + ", " : "") +
            "}";
    }

}
