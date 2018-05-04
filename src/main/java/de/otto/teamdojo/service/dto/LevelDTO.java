package de.otto.teamdojo.service.dto;

import javax.persistence.Lob;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Level entity.
 */
public class LevelDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    private String description;

    @Lob
    private byte[] picture;
    private String pictureContentType;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "1")
    private Float requiredScore;

    private Long dimensionId;

    private String dimensionName;

    private Long dependsOnId;

    private String dependsOnName;

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

    public Float getRequiredScore() {
        return requiredScore;
    }

    public void setRequiredScore(Float requiredScore) {
        this.requiredScore = requiredScore;
    }

    public Long getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(Long dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public Long getDependsOnId() {
        return dependsOnId;
    }

    public void setDependsOnId(Long levelId) {
        this.dependsOnId = levelId;
    }

    public String getDependsOnName() {
        return dependsOnName;
    }

    public void setDependsOnName(String levelName) {
        this.dependsOnName = levelName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LevelDTO levelDTO = (LevelDTO) o;
        if (levelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), levelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LevelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", picture='" + getPicture() + "'" +
            ", requiredScore=" + getRequiredScore() +
            ", dimension=" + getDimensionId() +
            ", dimension='" + getDimensionName() + "'" +
            ", dependsOn=" + getDependsOnId() +
            ", dependsOn='" + getDependsOnName() + "'" +
            "}";
    }
}
