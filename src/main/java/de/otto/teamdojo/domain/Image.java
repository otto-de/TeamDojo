package de.otto.teamdojo.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Image.
 */
@Entity
@Table(name = "image")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "small")
    private byte[] small;

    @Column(name = "small_content_type")
    private String smallContentType;

    @Lob
    @Column(name = "medium")
    private byte[] medium;

    @Column(name = "medium_content_type")
    private String mediumContentType;

    @Lob
    @Column(name = "large")
    private byte[] large;

    @Column(name = "large_content_type")
    private String largeContentType;

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

    public void setName(String name) {
        this.name = name;
    }

    public Image name(String name) {
        this.name = name;
        return this;
    }

    public byte[] getSmall() {
        return small;
    }

    public void setSmall(byte[] small) {
        this.small = small;
    }

    public Image small(byte[] small) {
        this.small = small;
        return this;
    }

    public String getSmallContentType() {
        return smallContentType;
    }

    public void setSmallContentType(String smallContentType) {
        this.smallContentType = smallContentType;
    }

    public Image smallContentType(String smallContentType) {
        this.smallContentType = smallContentType;
        return this;
    }

    public byte[] getMedium() {
        return medium;
    }

    public void setMedium(byte[] medium) {
        this.medium = medium;
    }

    public Image medium(byte[] medium) {
        this.medium = medium;
        return this;
    }

    public String getMediumContentType() {
        return mediumContentType;
    }

    public void setMediumContentType(String mediumContentType) {
        this.mediumContentType = mediumContentType;
    }

    public Image mediumContentType(String mediumContentType) {
        this.mediumContentType = mediumContentType;
        return this;
    }

    public byte[] getLarge() {
        return large;
    }

    public void setLarge(byte[] large) {
        this.large = large;
    }

    public Image large(byte[] large) {
        this.large = large;
        return this;
    }

    public String getLargeContentType() {
        return largeContentType;
    }

    public void setLargeContentType(String largeContentType) {
        this.largeContentType = largeContentType;
    }

    public Image largeContentType(String largeContentType) {
        this.largeContentType = largeContentType;
        return this;
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
        Image image = (Image) o;
        if (image.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), image.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Image{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", small='" + getSmall() + "'" +
            ", smallContentType='" + getSmallContentType() + "'" +
            ", medium='" + getMedium() + "'" +
            ", mediumContentType='" + getMediumContentType() + "'" +
            ", large='" + getLarge() + "'" +
            ", largeContentType='" + getLargeContentType() + "'" +
            "}";
    }
}
