package de.otto.teamdojo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Skill.
 */
@Entity
@Table(name = "skill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 5, max = 80)
    @Column(name = "title", length = 80, nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "implementation")
    private String implementation;

    @Column(name = "jhi_validation")
    private String validation;

    @Pattern(regexp = "^P(?:([-+]?[0-9]+)Y)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)W)?(?:([-+]?[0-9]+)D)?$")
    @Column(name = "expiry_period")
    private String expiryPeriod;

    @NotNull
    @Min(value = 0)
    @Column(name = "score", nullable = false)
    private Integer score;

    @OneToMany(mappedBy = "skill")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TeamSkill> teams = new HashSet<>();

    @OneToMany(mappedBy = "skill")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BadgeSkill> badges = new HashSet<>();

    @OneToMany(mappedBy = "skill")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LevelSkill> levels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Skill title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Skill description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImplementation() {
        return implementation;
    }

    public Skill implementation(String implementation) {
        this.implementation = implementation;
        return this;
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }

    public String getValidation() {
        return validation;
    }

    public Skill validation(String validation) {
        this.validation = validation;
        return this;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getExpiryPeriod() {
        return expiryPeriod;
    }

    public Skill expiryPeriod(String expiryPeriod) {
        this.expiryPeriod = expiryPeriod;
        return this;
    }

    public void setExpiryPeriod(String expiryPeriod) {
        this.expiryPeriod = expiryPeriod;
    }

    public Integer getScore() {
        return score;
    }

    public Skill score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Set<TeamSkill> getTeams() {
        return teams;
    }

    public Skill teams(Set<TeamSkill> teamSkills) {
        this.teams = teamSkills;
        return this;
    }

    public Skill addTeams(TeamSkill teamSkill) {
        this.teams.add(teamSkill);
        teamSkill.setSkill(this);
        return this;
    }

    public Skill removeTeams(TeamSkill teamSkill) {
        this.teams.remove(teamSkill);
        teamSkill.setSkill(null);
        return this;
    }

    public void setTeams(Set<TeamSkill> teamSkills) {
        this.teams = teamSkills;
    }

    public Set<BadgeSkill> getBadges() {
        return badges;
    }

    public Skill badges(Set<BadgeSkill> badgeSkills) {
        this.badges = badgeSkills;
        return this;
    }

    public Skill addBadges(BadgeSkill badgeSkill) {
        this.badges.add(badgeSkill);
        badgeSkill.setSkill(this);
        return this;
    }

    public Skill removeBadges(BadgeSkill badgeSkill) {
        this.badges.remove(badgeSkill);
        badgeSkill.setSkill(null);
        return this;
    }

    public void setBadges(Set<BadgeSkill> badgeSkills) {
        this.badges = badgeSkills;
    }

    public Set<LevelSkill> getLevels() {
        return levels;
    }

    public Skill levels(Set<LevelSkill> levelSkills) {
        this.levels = levelSkills;
        return this;
    }

    public Skill addLevels(LevelSkill levelSkill) {
        this.levels.add(levelSkill);
        levelSkill.setSkill(this);
        return this;
    }

    public Skill removeLevels(LevelSkill levelSkill) {
        this.levels.remove(levelSkill);
        levelSkill.setSkill(null);
        return this;
    }

    public void setLevels(Set<LevelSkill> levelSkills) {
        this.levels = levelSkills;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Skill skill = (Skill) o;
        if (skill.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skill.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Skill{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", implementation='" + getImplementation() + "'" +
            ", validation='" + getValidation() + "'" +
            ", expiryPeriod='" + getExpiryPeriod() + "'" +
            ", score=" + getScore() +
            "}";
    }
}
