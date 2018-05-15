package de.otto.teamdojo.service.dto;

import java.time.Instant;

/**
 * A DTO representing skills that can be achieved by a team
 */
public class AchievableSkillDTO {

    private Long teamSkillId;
    private Long skillId;
    private String title;
    private String description;
    private Instant achievedAt;
    private Boolean irrelevant;

    public AchievableSkillDTO() {
        // Empty constructor needed for Jackson.
    }

    public AchievableSkillDTO(Long teamSkillId, Long skillId, String title, String description, Instant achievedAt, Boolean irrelevant) {
        this.teamSkillId = teamSkillId;
        this.skillId = skillId;
        this.title = title;
        this.description = description;
        this.achievedAt = achievedAt;
        this.irrelevant = irrelevant;
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

    public String getDescription() {
        return description;
    }

    public Instant getAchievedAt() {
        return achievedAt;
    }

    public void setAchievedAt(Instant achievedAt) {
        this.achievedAt = achievedAt;
    }

    public Boolean isIrrelevant() { return irrelevant; }

    public void setIrrelevant(Boolean irrelevant){
        this.irrelevant = irrelevant;
    }
}
