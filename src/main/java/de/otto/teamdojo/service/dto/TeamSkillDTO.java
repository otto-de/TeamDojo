package de.otto.teamdojo.service.dto;

import de.otto.teamdojo.domain.enumeration.SkillStatus;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the TeamSkill entity.
 */
public class TeamSkillDTO implements Serializable {

    private Long id;

    private Instant completedAt;

    private Instant verifiedAt;

    private Boolean irrelevant;

    private String note;

    private Long skillId;

    private String skillTitle;

    private Integer skillExpiryPeriod;

    private Long teamId;

    private String teamName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
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

    public void setSkillExpiryPeriod(Integer skillExpiryPeriod) {
        this.skillExpiryPeriod = skillExpiryPeriod;
    }

    public SkillStatus getSkillStatus() {
        return SkillStatus.determineSkillStatus(irrelevant, completedAt, skillExpiryPeriod);
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
            ", completedAt='" + getCompletedAt() + "'" +
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
