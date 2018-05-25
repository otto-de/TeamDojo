package de.otto.teamdojo.service.dto;

import javax.persistence.Lob;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Badge entity.
 */
public class BadgeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 20)
    private String name;

    private String description;

    @Lob
    private byte[] picture;
    private String pictureContentType;

    private Instant availableUntil;

    @Min(value = 1)
    private Integer availableAmount;

    @NotNull
    @DecimalMin(value = "0")
    private Double multiplier;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "1")
    private Double requiredScore;

    private Set<DimensionDTO> dimensions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public Instant getAvailableUntil() {
        return availableUntil;
    }

    public void setAvailableUntil(Instant availableUntil) {
        this.availableUntil = availableUntil;
    }

    public Integer getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(Integer availableAmount) {
        this.availableAmount = availableAmount;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public Double getRequiredScore() {
        return requiredScore;
    }

    public void setRequiredScore(Double requiredScore) {
        this.requiredScore = requiredScore;
    }

    public Set<DimensionDTO> getDimensions() {
        return dimensions;
    }

    public void setDimensions(Set<DimensionDTO> dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BadgeDTO badgeDTO = (BadgeDTO) o;
        if (badgeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), badgeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BadgeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", picture='" + getPicture() + "'" +
            ", availableUntil='" + getAvailableUntil() + "'" +
            ", availableAmount=" + getAvailableAmount() +
            ", multiplier=" + getMultiplier() +
            ", requiredScore=" + getRequiredScore() +
            "}";
    }
}
