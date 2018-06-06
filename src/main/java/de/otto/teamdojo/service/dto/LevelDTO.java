package de.otto.teamdojo.service.dto;

import javax.validation.constraints.*;
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

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "1")
    private Double requiredScore;

    @NotNull
    @DecimalMin(value = "0")
    private Double instantMultiplier;

    @Min(value = 0)
    private Integer completionBonus;

    private Long dimensionId;

    private String dimensionName;

    private Long dependsOnId;

    private String dependsOnName;

    private Long imageId;

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

    public Double getRequiredScore() {
        return requiredScore;
    }

    public void setRequiredScore(Double requiredScore) {
        this.requiredScore = requiredScore;
    }

    public Double getInstantMultiplier() {
        return instantMultiplier;
    }

    public void setInstantMultiplier(Double instantMultiplier) {
        this.instantMultiplier = instantMultiplier;
    }

    public Integer getCompletionBonus() {
        return completionBonus;
    }

    public void setCompletionBonus(Integer completionBonus) {
        this.completionBonus = completionBonus;
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

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
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
            ", requiredScore=" + getRequiredScore() +
            ", instantMultiplier=" + getInstantMultiplier() +
            ", completionBonus=" + getCompletionBonus() +
            ", dimension=" + getDimensionId() +
            ", dimension='" + getDimensionName() + "'" +
            ", dependsOn=" + getDependsOnId() +
            ", dependsOn='" + getDependsOnName() + "'" +
            ", image=" + getImageId() +
            "}";
    }
}
