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


    public static Skill inputValidation(EntityManager em) {
        return persistSkill(inputValidation(), em);
    }

    public static Skill inputValidation() {
        return new Skill()
            .title(INPUT_VALIDATION_TITLE)
            .description(INPUT_VALIDATION_DESC)
            .validation(INPUT_VALIDATION_VAL)
            .implementation(INPUT_VALIDATION_IMPL);
    }

    public static Skill softwareUpdates(EntityManager em) {
        return persistSkill(softwareUpdates(), em);
    }

    public static Skill softwareUpdates() {
        return new Skill()
            .title(SOFTWARE_UPDATES_TITLE)
            .description(SOFTWARE_UPDATES_DESC)
            .validation(SOFTWARE_UPDATES_VAL)
            .implementation(SOFTWARE_UPDATES_IMPL);
    }

    public static Skill strongPasswords(EntityManager em) {
        return persistSkill(strongPasswords(), em);
    }

    public static Skill strongPasswords() {
        return new Skill()
            .title(STRONG_PASSWORDS_TITLE)
            .description(STRONG_PASSWORDS_DESC)
            .validation(STRONG_PASSWORDS_VAL)
            .implementation(STRONG_PASSWORDS_IMPL);
    }

    public static Skill evilUserStories(EntityManager em) {
        return persistSkill(evilUserStories(), em);
    }

    public static Skill evilUserStories() {
        return new Skill()
            .title(EVIL_USER_STORIES_TITLE)
            .description(EVIL_USER_STORIES_DESC)
            .validation(EVIL_USER_STORIES_VAL)
            .implementation(EVIL_USER_STORIES_IMPL);
    }

    private static Skill persistSkill(Skill skill, EntityManager em) {
        em.persist(skill);
        return skill;
    }

}
