package de.otto.teamdojo.service.dto;

import java.time.Instant;

/**
 * A DTO representing skills that can be achieved by a team
 */
public class SkillRateDTO {

    private Integer rate;
    private Long skillId;

    public SkillRateDTO() {
        // Empty constructor needed for Jackson.
    }

    public SkillRateDTO(Long skillId, Integer rate) {
        this.rate = rate;
        this.skillId = skillId;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Long getSkillId() {
        return skillId;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }
}
