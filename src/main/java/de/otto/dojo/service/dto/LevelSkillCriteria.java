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
 * Criteria class for the LevelSkill entity. This class is used in LevelSkillResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /level-skills?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LevelSkillCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private IntegerFilter score;

    private LongFilter skillId;

    private LongFilter levelId;

    public LevelSkillCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getScore() {
        return score;
    }

    public void setScore(IntegerFilter score) {
        this.score = score;
    }

    public LongFilter getSkillId() {
        return skillId;
    }

    public void setSkillId(LongFilter skillId) {
        this.skillId = skillId;
    }

    public LongFilter getLevelId() {
        return levelId;
    }

    public void setLevelId(LongFilter levelId) {
        this.levelId = levelId;
    }

    @Override
    public String toString() {
        return "LevelSkillCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (score != null ? "score=" + score + ", " : "") +
                (skillId != null ? "skillId=" + skillId + ", " : "") +
                (levelId != null ? "levelId=" + levelId + ", " : "") +
            "}";
    }

}
