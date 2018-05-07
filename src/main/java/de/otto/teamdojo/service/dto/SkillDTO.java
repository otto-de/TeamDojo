package de.otto.teamdojo.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Skill entity.
 */
public class SkillDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 5, max = 80)
    private String title;

    private String description;

    private String implementation;

    private String validation;

    @Pattern(regexp = "^P(?:([-+]?[0-9]+)Y)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)W)?(?:([-+]?[0-9]+)D)?$")
    private String expiryPeriod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImplementation() {
        return implementation;
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getExpiryPeriod() {
        return expiryPeriod;
    }

    public void setExpiryPeriod(String expiryPeriod) {
        this.expiryPeriod = expiryPeriod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SkillDTO skillDTO = (SkillDTO) o;
        if (skillDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skillDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SkillDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", implementation='" + getImplementation() + "'" +
            ", validation='" + getValidation() + "'" +
            ", expiryPeriod='" + getExpiryPeriod() + "'" +
            "}";
    }
}
