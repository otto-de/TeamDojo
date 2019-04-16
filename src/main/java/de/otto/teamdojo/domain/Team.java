package de.otto.teamdojo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    @Size(min = 2, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Size(min = 2, max = 6)
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$")
    @Column(name = "short_name", length = 6, nullable = false)
    private String shortName;

    @Column(name = "slogan")
    private String slogan;

    @Column(name = "contact_person")
    private String contactPerson;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "team_participations",
        joinColumns = @JoinColumn(name = "teams_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "participations_id", referencedColumnName = "id"))
    private Set<Dimension> participations = new HashSet<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TeamSkill> skills = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
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

    public void setName(String name) {
        this.name = name;
    }

    public Team name(String name) {
        this.name = name;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Team shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public Team slogan(String slogan) {
        this.slogan = slogan;
        return this;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Team contactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
        return this;
    }

    public Set<Dimension> getParticipations() {
        return participations;
    }

    public void setParticipations(Set<Dimension> dimensions) {
        this.participations = dimensions;
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

    public Set<TeamSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<TeamSkill> teamSkills) {
        this.skills = teamSkills;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Team image(Image image) {
        this.image = image;
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
            ", shortName='" + getShortName() + "'" +
            ", slogan='" + getSlogan() + "'" +
            ", contactPerson='" + getContactPerson() + "'" +
            "}";
    }
}
