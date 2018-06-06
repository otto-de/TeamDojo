package de.otto.teamdojo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Level.
 */
@Entity
@Table(name = "level")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Level implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "1")
    @Column(name = "required_score", nullable = false)
    private Double requiredScore;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "instant_multiplier", nullable = false)
    private Double instantMultiplier;

    @Min(value = 0)
    @Column(name = "completion_bonus")
    private Integer completionBonus;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties()
    private Dimension dimension;

    @OneToOne
    @JoinColumn(unique = true)
    private Level dependsOn;

    @OneToMany(mappedBy = "level")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LevelSkill> skills = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private Image image;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Level name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Level description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRequiredScore() {
        return requiredScore;
    }

    public Level requiredScore(Double requiredScore) {
        this.requiredScore = requiredScore;
        return this;
    }

    public void setRequiredScore(Double requiredScore) {
        this.requiredScore = requiredScore;
    }

    public Double getInstantMultiplier() {
        return instantMultiplier;
    }

    public Level instantMultiplier(Double instantMultiplier) {
        this.instantMultiplier = instantMultiplier;
        return this;
    }

    public void setInstantMultiplier(Double instantMultiplier) {
        this.instantMultiplier = instantMultiplier;
    }

    public Integer getCompletionBonus() {
        return completionBonus;
    }

    public Level completionBonus(Integer completionBonus) {
        this.completionBonus = completionBonus;
        return this;
    }

    public void setCompletionBonus(Integer completionBonus) {
        this.completionBonus = completionBonus;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public Level dimension(Dimension dimension) {
        this.dimension = dimension;
        return this;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public Level getDependsOn() {
        return dependsOn;
    }

    public Level dependsOn(Level level) {
        this.dependsOn = level;
        return this;
    }

    public void setDependsOn(Level level) {
        this.dependsOn = level;
    }

    public Set<LevelSkill> getSkills() {
        return skills;
    }

    public Level skills(Set<LevelSkill> levelSkills) {
        this.skills = levelSkills;
        return this;
    }

    public Level addSkills(LevelSkill levelSkill) {
        this.skills.add(levelSkill);
        levelSkill.setLevel(this);
        return this;
    }

    public Level removeSkills(LevelSkill levelSkill) {
        this.skills.remove(levelSkill);
        levelSkill.setLevel(null);
        return this;
    }

    public void setSkills(Set<LevelSkill> levelSkills) {
        this.skills = levelSkills;
    }

    public Image getImage() {
        return image;
    }

    public Level image(Image image) {
        this.image = image;
        return this;
    }

    public void setImage(Image image) {
        this.image = image;
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
        Level level = (Level) o;
        if (level.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), level.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Level{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", requiredScore=" + getRequiredScore() +
            ", instantMultiplier=" + getInstantMultiplier() +
            ", completionBonus=" + getCompletionBonus() +
            "}";
    }
}
