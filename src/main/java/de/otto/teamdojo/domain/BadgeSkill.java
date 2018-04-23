package de.otto.teamdojo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BadgeSkill.
 */
@Entity
@Table(name = "badge_skill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BadgeSkill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "score", nullable = false)
    private Integer score;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("skills")
    private Badge badge;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("badges")
    private Skill skill;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public BadgeSkill score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Badge getBadge() {
        return badge;
    }

    public BadgeSkill badge(Badge badge) {
        this.badge = badge;
        return this;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public Skill getSkill() {
        return skill;
    }

    public BadgeSkill skill(Skill skill) {
        this.skill = skill;
        return this;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
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
        BadgeSkill badgeSkill = (BadgeSkill) o;
        if (badgeSkill.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), badgeSkill.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BadgeSkill{" +
            "id=" + getId() +
            ", score=" + getScore() +
            "}";
    }
}
