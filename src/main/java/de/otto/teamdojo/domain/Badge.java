package de.otto.teamdojo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Badge.
 */
@Entity
@Table(name = "badge")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Badge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "picture_content_type")
    private String pictureContentType;

    @Column(name = "available_until")
    private Instant availableUntil;

    @Min(value = 1)
    @Column(name = "available_amount")
    private Integer availableAmount;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "multiplier", nullable = false)
    private Double multiplier;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "1")
    @Column(name = "required_score", nullable = false)
    private Double requiredScore;

    @OneToMany(mappedBy = "badge")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BadgeSkill> skills = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "badge_dimensions",
        joinColumns = @JoinColumn(name = "badges_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "dimensions_id", referencedColumnName = "id"))
    private Set<Dimension> dimensions = new HashSet<>();

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

    public Badge name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Badge description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public Badge picture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public Badge pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public Instant getAvailableUntil() {
        return availableUntil;
    }

    public Badge availableUntil(Instant availableUntil) {
        this.availableUntil = availableUntil;
        return this;
    }

    public void setAvailableUntil(Instant availableUntil) {
        this.availableUntil = availableUntil;
    }

    public Integer getAvailableAmount() {
        return availableAmount;
    }

    public Badge availableAmount(Integer availableAmount) {
        this.availableAmount = availableAmount;
        return this;
    }

    public void setAvailableAmount(Integer availableAmount) {
        this.availableAmount = availableAmount;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public Badge multiplier(Double multiplier) {
        this.multiplier = multiplier;
        return this;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public Double getRequiredScore() {
        return requiredScore;
    }

    public Badge requiredScore(Double requiredScore) {
        this.requiredScore = requiredScore;
        return this;
    }

    public void setRequiredScore(Double requiredScore) {
        this.requiredScore = requiredScore;
    }

    public Set<BadgeSkill> getSkills() {
        return skills;
    }

    public Badge skills(Set<BadgeSkill> badgeSkills) {
        this.skills = badgeSkills;
        return this;
    }

    public Badge addSkills(BadgeSkill badgeSkill) {
        this.skills.add(badgeSkill);
        badgeSkill.setBadge(this);
        return this;
    }

    public Badge removeSkills(BadgeSkill badgeSkill) {
        this.skills.remove(badgeSkill);
        badgeSkill.setBadge(null);
        return this;
    }

    public void setSkills(Set<BadgeSkill> badgeSkills) {
        this.skills = badgeSkills;
    }

    public Set<Dimension> getDimensions() {
        return dimensions;
    }

    public Badge dimensions(Set<Dimension> dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    public Badge addDimensions(Dimension dimension) {
        this.dimensions.add(dimension);
        dimension.getBadges().add(this);
        return this;
    }

    public Badge removeDimensions(Dimension dimension) {
        this.dimensions.remove(dimension);
        dimension.getBadges().remove(this);
        return this;
    }

    public void setDimensions(Set<Dimension> dimensions) {
        this.dimensions = dimensions;
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
        Badge badge = (Badge) o;
        if (badge.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), badge.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Badge{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + getPictureContentType() + "'" +
            ", availableUntil='" + getAvailableUntil() + "'" +
            ", availableAmount=" + getAvailableAmount() +
            ", multiplier=" + getMultiplier() +
            ", requiredScore=" + getRequiredScore() +
            "}";
    }
}
