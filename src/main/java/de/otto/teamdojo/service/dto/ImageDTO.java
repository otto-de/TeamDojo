package de.otto.teamdojo.service.dto;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Image entity.
 */
public class ImageDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @Lob
    private byte[] small;
    private String smallContentType;

    @Lob
    private byte[] medium;
    private String mediumContentType;

    @Lob
    private byte[] large;
    private String largeContentType;

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

    public byte[] getSmall() {
        return small;
    }

    public void setSmall(byte[] small) {
        this.small = small;
    }

    public String getSmallContentType() {
        return smallContentType;
    }

    public void setSmallContentType(String smallContentType) {
        this.smallContentType = smallContentType;
    }

    public byte[] getMedium() {
        return medium;
    }

    public void setMedium(byte[] medium) {
        this.medium = medium;
    }

    public String getMediumContentType() {
        return mediumContentType;
    }

    public void setMediumContentType(String mediumContentType) {
        this.mediumContentType = mediumContentType;
    }

    public byte[] getLarge() {
        return large;
    }

    public void setLarge(byte[] large) {
        this.large = large;
    }

    public String getLargeContentType() {
        return largeContentType;
    }

    public void setLargeContentType(String largeContentType) {
        this.largeContentType = largeContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImageDTO imageDTO = (ImageDTO) o;
        if (imageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), imageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ImageDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", small='" + getSmall() + "'" +
            ", medium='" + getMedium() + "'" +
            ", large='" + getLarge() + "'" +
            "}";
    }
}
