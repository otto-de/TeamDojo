package de.otto.teamdojo.service.dto;

import io.github.jhipster.service.filter.LongFilter;

import java.io.Serializable;

public class AchievableSkillCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter teamId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public LongFilter getTeamId() {
        return teamId;
    }

    public void setTeamId(LongFilter teamId) {
        this.teamId = teamId;
    }
}
