package de.otto.dojo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "picture_content_type")
    private String pictureContentType;

    @Column(name = "contact_person")
    private String contactPerson;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "team_participations",
               joinColumns = @JoinColumn(name="teams_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="participations_id", referencedColumnName="id"))
    private Set<Dimension> participations = new HashSet<>();

    @OneToMany(mappedBy = "team")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TeamSkill> skills = new HashSet<>();

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

    public Team name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicture() {
        return picture;
    }

    public Team picture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public Team pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public Team contactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
        return this;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Set<Dimension> getParticipations() {
        return participations;
    }

    public Team participations(Set<Dimension> dimensions) {
        this.participations = dimensions;
        return this;
    }

    public Team addParticipations(Dimension dimension) {
        this.participations.add(dimension);
        dimension.getParticipants().add(this);
        return this;
    }

    public Team removeParticipations(Dimension dimension) {
        this.participations.remove(dimension);
        dimension.getParticipants().remove(this);
        return this;
    }

    public void setParticipations(Set<Dimension> dimensions) {
        this.participations = dimensions;
    }

    public Set<TeamSkill> getSkills() {
        return skills;
    }

    public Team skills(Set<TeamSkill> teamSkills) {
        this.skills = teamSkills;
        return this;
    }

    public Team addSkills(TeamSkill teamSkill) {
        this.skills.add(teamSkill);
        teamSkill.setTeam(this);
        return this;
    }

    public Team removeSkills(TeamSkill teamSkill) {
        this.skills.remove(teamSkill);
        teamSkill.setTeam(null);
        return this;
    }

    public void setSkills(Set<TeamSkill> teamSkills) {
        this.skills = teamSkills;
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
        Team team = (Team) o;
        if (team.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), team.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + getPictureContentType() + "'" +
            ", contactPerson='" + getContactPerson() + "'" +
            "}";
    }
}
