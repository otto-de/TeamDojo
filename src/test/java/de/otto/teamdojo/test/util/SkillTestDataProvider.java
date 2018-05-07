package de.otto.teamdojo.test.util;

import de.otto.teamdojo.domain.Skill;

import javax.persistence.EntityManager;

public class SkillTestDataProvider {


    private static final String DESCRIPTION = " Description";
    private static final String IMPLEMENTATION = " Implementation";
    private static final String VALIDATION = " Validation";

    private static final String INPUT_VALIDATION = "Input Validation";
    public static final String INPUT_VALIDATION_TITLE = INPUT_VALIDATION;
    public static final String INPUT_VALIDATION_DESC = INPUT_VALIDATION + DESCRIPTION;
    public static final String INPUT_VALIDATION_VAL = INPUT_VALIDATION + IMPLEMENTATION;
    public static final String INPUT_VALIDATION_IMPL = INPUT_VALIDATION + VALIDATION;

    private static final String SOFTWARE_UPDATES = "Software updates";
    public static final String SOFTWARE_UPDATES_TITLE = SOFTWARE_UPDATES;
    public static final String SOFTWARE_UPDATES_DESC = SOFTWARE_UPDATES + DESCRIPTION;
    public static final String SOFTWARE_UPDATES_VAL = SOFTWARE_UPDATES + IMPLEMENTATION;
    public static final String SOFTWARE_UPDATES_IMPL = SOFTWARE_UPDATES + VALIDATION;

    private static final String STRONG_PASSWORDS = "Strong passwords";
    public static final String STRONG_PASSWORDS_TITLE = STRONG_PASSWORDS;
    public static final String STRONG_PASSWORDS_DESC = STRONG_PASSWORDS + DESCRIPTION;
    public static final String STRONG_PASSWORDS_VAL = STRONG_PASSWORDS + IMPLEMENTATION;
    public static final String STRONG_PASSWORDS_IMPL = STRONG_PASSWORDS + VALIDATION;

    private static final String EVIL_USER_STORIES = "Evil user stories";
    private static final String EVIL_USER_STORIES_TITLE = EVIL_USER_STORIES;
    private static final String EVIL_USER_STORIES_DESC = EVIL_USER_STORIES + DESCRIPTION;
    private static final String EVIL_USER_STORIES_VAL = EVIL_USER_STORIES + VALIDATION;
    private static final String EVIL_USER_STORIES_IMPL = EVIL_USER_STORIES + IMPLEMENTATION;

    private static final String DOCKERIZED = "Dockerized";
    public static final String DOCKERIZED_TITLE = DOCKERIZED;
    public static final String DOCKERIZED_DESC = DOCKERIZED + DESCRIPTION;
    public static final String DOCKERIZED_VAL = DOCKERIZED + IMPLEMENTATION;
    public static final String DOCKERIZED_IMPL = DOCKERIZED + VALIDATION;

    public static SkillBuilder skill(String title) {
        return new SkillBuilder(title);
    }

    public static SkillBuilder inputValidation() {
        return skill(INPUT_VALIDATION_TITLE)
            .description(INPUT_VALIDATION_DESC)
            .validation(INPUT_VALIDATION_VAL)
            .implementation(INPUT_VALIDATION_IMPL);
    }

    public static SkillBuilder softwareUpdates() {
        return skill(SOFTWARE_UPDATES_TITLE)
            .description(SOFTWARE_UPDATES_DESC)
            .validation(SOFTWARE_UPDATES_VAL)
            .implementation(SOFTWARE_UPDATES_IMPL);
    }


    public static SkillBuilder strongPasswords() {
        return skill(STRONG_PASSWORDS_TITLE)
            .description(STRONG_PASSWORDS_DESC)
            .validation(STRONG_PASSWORDS_VAL)
            .implementation(STRONG_PASSWORDS_IMPL);
    }

    public static SkillBuilder evilUserStories() {
        return skill(EVIL_USER_STORIES_TITLE)
            .description(EVIL_USER_STORIES_DESC)
            .validation(EVIL_USER_STORIES_VAL)
            .implementation(EVIL_USER_STORIES_IMPL);
    }


    public static SkillBuilder dockerized() {
        return skill(DOCKERIZED_TITLE)
            .description(DOCKERIZED_DESC)
            .validation(DOCKERIZED_VAL)
            .implementation(DOCKERIZED_IMPL);
    }

    public static class SkillBuilder {

        private final String title;
        private String description;
        private String validation;
        private String implementation;

        public SkillBuilder(String title) {
            this.title = title;
        }

        public SkillBuilder description(String description) {
            this.description = description;
            return this;
        }

        public SkillBuilder validation(String validation) {
            this.validation = validation;
            return this;
        }

        public SkillBuilder implementation(String implementation) {
            this.implementation = implementation;
            return this;
        }

        public Skill build(EntityManager em) {
            Skill skill = build();
            em.persist(skill);
            return skill;
        }

        public Skill build() {
            return new Skill()
                .title(title)
                .description(description)
                .validation(validation)
                .implementation(implementation);
        }
    }

}
