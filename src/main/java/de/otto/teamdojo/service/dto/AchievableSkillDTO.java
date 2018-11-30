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
    private Instant verifiedAt;
    private Integer vote;
    private String voters;
    private Boolean irrelevant;
    private Double rateScore;
    private Integer rateCount;

    public AchievableSkillDTO() {
        // Empty constructor needed for Jackson.
    }

    public AchievableSkillDTO(
        Long teamSkillId,
        Long skillId,
        String title,
        String description,
        Instant achievedAt,
        Instant verifiedAt,
        Integer vote,
        String voters,
        Boolean irrelevant,
        Double rateScore,
        Integer rateCount) {
        this.teamSkillId = teamSkillId;
        this.skillId = skillId;
        this.title = title;
        this.description = description;
        this.achievedAt = achievedAt;
        this.verifiedAt = verifiedAt;
        this.vote = vote;
        this.voters = voters;
        this.irrelevant = irrelevant;
        this.rateScore = rateScore;
        this.rateCount = rateCount;
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

    public Instant getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Instant verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    public String getVoters() {
        return voters;
    }

    public void setVoters(String voters) {
        this.voters = voters;
    }

    public Boolean isIrrelevant() { return irrelevant; }

    public void setIrrelevant(Boolean irrelevant){
        this.irrelevant = irrelevant;
    }

    public Double getRateScore() {
        return rateScore;
    }

    public void setRateScore(Double rateScore) {
        this.rateScore = rateScore;
    }

    public Integer getRateCount() {
        return rateCount;
    }

    public void setRateCount(Integer rateCount) {
        this.rateCount = rateCount;
    }


}
