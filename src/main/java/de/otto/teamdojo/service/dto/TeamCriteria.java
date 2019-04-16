package de.otto.teamdojo.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;


/**
 * Criteria class for the Team entity. This class is used in TeamResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /teams?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TeamCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private StringFilter shortName;

    private StringFilter slogan;

    private StringFilter contactPerson;

    private LongFilter participationsId;

    private LongFilter skillsId;

    private LongFilter imageId;

    public TeamCriteria() {
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

    public StringFilter getShortName() {
        return shortName;
    }

    public void setShortName(StringFilter shortName) {
        this.shortName = shortName;
    }

    public StringFilter getSlogan() {
        return slogan;
    }

    public void setSlogan(StringFilter slogan) {
        this.slogan = slogan;
    }

    public StringFilter getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(StringFilter contactPerson) {
        this.contactPerson = contactPerson;
    }

    public LongFilter getParticipationsId() {
        return participationsId;
    }

    public void setParticipationsId(LongFilter participationsId) {
        this.participationsId = participationsId;
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
        return "TeamCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (shortName != null ? "shortName=" + shortName + ", " : "") +
            (slogan != null ? "slogan=" + slogan + ", " : "") +
            (contactPerson != null ? "contactPerson=" + contactPerson + ", " : "") +
            (participationsId != null ? "participationsId=" + participationsId + ", " : "") +
            (skillsId != null ? "skillsId=" + skillsId + ", " : "") +
            (imageId != null ? "imageId=" + imageId + ", " : "") +
            "}";
    }

}
