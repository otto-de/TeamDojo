package de.otto.teamdojo.test.util;

import de.otto.teamdojo.domain.Team;

import javax.persistence.EntityManager;

public class TeamTestDataProvider {

    public static final String FT1_NAME = "Feature Team 1";
    public static final String FT1_SHORT_NAME = "FT1";
    public static final String FT1_SLOGAN = "Yes we can";
    public static final String FT1_CONTACT_PERSON = "ft1@example.com";

    public static final String FT2_NAME = "Feature Team 2";
    public static final String FT2_SHORT_NAME = "FT2";
    public static final String FT2_SLOGAN = "Just do it";
    public static final String FT2_CONTACT_PERSON = "ft2@example.com";

    public static TeamBuilder team(String name, String shortName) {
        return new TeamBuilder(name, shortName);
    }

    public static TeamBuilder ft1() {
        return new TeamBuilder(FT1_NAME, FT1_SHORT_NAME).slogan(FT1_SLOGAN).contactPerson(FT1_CONTACT_PERSON);
    }

    public static TeamBuilder ft2() {
        return new TeamBuilder(FT2_NAME, FT2_SHORT_NAME).slogan(FT2_SLOGAN).contactPerson(FT2_CONTACT_PERSON);
    }


    public static class TeamBuilder {

        private final String name;
        private final String shortName;
        private String slogan;
        private String contactPerson;

        public TeamBuilder(String name, String shortName) {
            this.name = name;
            this.shortName = shortName;
        }

        public TeamBuilder slogan(String slogan) {
            this.slogan = slogan;
            return this;
        }

        public TeamBuilder contactPerson(String contactPerson) {
            this.contactPerson = contactPerson;
            return this;
        }

        public Team build(EntityManager em) {
            Team team = build();
            em.persist(team);
            return team;
        }

        public Team build() {
            return new Team().name(name).shortName(shortName).slogan(slogan).contactPerson(contactPerson);
        }
    }

}
