package de.otto.teamdojo.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the BadgeSkill entity.
 */
public class BadgeSkillDTO implements Serializable {

    private Long id;

    private Long badgeId;

    private String badgeName;

    private Long skillId;

    private String skillTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BadgeSkillDTO badgeSkillDTO = (BadgeSkillDTO) o;
        if (badgeSkillDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), badgeSkillDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BadgeSkillDTO{" +
            "id=" + getId() +
            ", badge=" + getBadgeId() +
            ", badge='" + getBadgeName() + "'" +
            ", skill=" + getSkillId() +
            ", skill='" + getSkillTitle() + "'" +
            "}";
    }
}
