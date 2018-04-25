package de.otto.teamdojo.service.dto;

import java.time.Instant;

/**
 * A DTO representing skills that can be achieved by a team
 */
public class AchievableSkillDTO {

    private Long teamSkillId;
    private Long skillId;
    private String title;
    private Instant achievedAt;

    public AchievableSkillDTO() {
        // Empty constructor needed for Jackson.
    }

    public AchievableSkillDTO(Long teamSkillId, Long skillId, String title, Instant achievedAt) {
        this.teamSkillId = teamSkillId;
        this.skillId = skillId;
        this.title = title;
        this.achievedAt = achievedAt;
    }

    public Long getTeamSkillId() {
        return teamSkillId;
    }

    public void setTeamSkillId(Long teamSkillId) {
        this.teamSkillId = teamSkillId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getAchievedAt() {
        return achievedAt;
    }

    public void setAchievedAt(Instant achievedAt) {
        this.achievedAt = achievedAt;
    }
}
