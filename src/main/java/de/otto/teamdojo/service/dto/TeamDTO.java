package de.otto.teamdojo.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Team entity.
 */
public class TeamDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @NotNull
    @Size(min = 2, max = 6)
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$")
    private String shortName;

    private String slogan;

    private String contactPerson;

    private Set<DimensionDTO> participations = new HashSet<>();

    private Long imageId;

    private String imageName;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Set<DimensionDTO> getParticipations() {
        return participations;
    }

    public void setParticipations(Set<DimensionDTO> dimensions) {
        this.participations = dimensions;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TeamDTO teamDTO = (TeamDTO) o;
        if (teamDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teamDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeamDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", slogan='" + getSlogan() + "'" +
            ", contactPerson='" + getContactPerson() + "'" +
            ", image=" + getImageId() +
            ", image='" + getImageName() + "'" +
            "}";
    }
}
