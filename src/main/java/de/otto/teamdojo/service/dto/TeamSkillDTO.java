package de.otto.teamdojo.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the TeamSkill entity.
 */
public class TeamSkillDTO implements Serializable {

    private Long id;

    private Instant achievedAt;

    private Instant verifiedAt;

    private Boolean irrelevant;

    private String note;

    private Long skillId;

    private String skillTitle;

    private Long teamId;

    private String teamName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getAchievedAt() {
        return achievedAt;
    }

    public void setAchievedAt(Instant achievedAt) {
        this.achievedAt = achievedAt;
    }

    public Instant getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Instant verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public Boolean isIrrelevant() {
        return irrelevant;
    }

    public void setIrrelevant(Boolean irrelevant) {
        this.irrelevant = irrelevant;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getSkillTitle() {
        return skillTitle;
    }

    public void setSkillTitle(String skillTitle) {
        this.skillTitle = skillTitle;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TeamSkillDTO teamSkillDTO = (TeamSkillDTO) o;
        if (teamSkillDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teamSkillDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeamSkillDTO{" +
            "id=" + getId() +
            ", achievedAt='" + getAchievedAt() + "'" +
            ", verifiedAt='" + getVerifiedAt() + "'" +
            ", irrelevant='" + isIrrelevant() + "'" +
            ", note='" + getNote() + "'" +
            ", skill=" + getSkillId() +
            ", skill='" + getSkillTitle() + "'" +
            ", team=" + getTeamId() +
            ", team='" + getTeamName() + "'" +
            "}";
    }
}
