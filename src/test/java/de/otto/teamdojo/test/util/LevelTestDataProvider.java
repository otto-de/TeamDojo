package de.otto.teamdojo.test.util;

import com.google.common.collect.Lists;
import de.otto.teamdojo.domain.Dimension;
import de.otto.teamdojo.domain.Level;
import de.otto.teamdojo.domain.LevelSkill;
import de.otto.teamdojo.domain.Skill;

import javax.persistence.EntityManager;
import java.util.List;

public class LevelTestDataProvider {

    public static String YELLOW_NAME = "Yellow Belt";
    public static String ORANGE_NAME = "Orange Belt";
    public static String GREEN_NAME = "Green Belt";
    public static String BLUE_NAME = "Blue Belt";
    public static String RED_NAME = "Red Belt";
    public static String BROWN_NAME = "Brown Belt";
    public static String BLACK_NAME = "Black Belt";


    public static LevelBuilder level(String name, Dimension dimension) {
        return new LevelBuilder(name, dimension);
    }

    public static LevelBuilder yellow(Dimension dimension) {
        return level(YELLOW_NAME, dimension).requiredScore(0.8f);
    }

    public static LevelBuilder orange(Dimension dimension) {
        return level(ORANGE_NAME, dimension).requiredScore(0.8f);
    }


    public static LevelBuilder green(Dimension dimension) {
        return level(GREEN_NAME, dimension).requiredScore(0.8f);
    }


    public static LevelBuilder blue(Dimension dimension) {
        return level(BLUE_NAME, dimension).requiredScore(0.8f);
    }


    public static LevelBuilder red(Dimension dimension) {
        return level(RED_NAME, dimension).requiredScore(0.8f);
    }


    public static LevelBuilder brown(Dimension dimension) {
        return level(BROWN_NAME, dimension).requiredScore(0.8f);
    }

    public static LevelBuilder black(Dimension dimension) {
        return level(BLACK_NAME, dimension).requiredScore(0.8f);
    }


    public static class LevelBuilder {

        private final String name;
        private final Dimension dimension;
        private float requiredScore = 1f;
        private Level dependsOn;
        private List<LevelSkill> skills = Lists.newArrayList();

        public LevelBuilder(String name, Dimension dimension) {
            this.name = name;
            this.dimension = dimension;
        }

        public LevelBuilder dependsOn(Level dependsOn) {
            this.dependsOn = dependsOn;
            return this;
        }

        public LevelBuilder requiredScore(float requiredScore) {
            this.requiredScore = requiredScore;
            return this;
        }

        public LevelBuilder addSkill(Skill skill) {
            return addSkill(skill, 100);
        }

        public LevelBuilder addSkill(Skill skill, Integer score) {
            LevelSkill levelSkill = new LevelSkill();
            levelSkill.setScore(score);
            skill.addLevels(levelSkill);
            skills.add(levelSkill);
            return this;
        }

        public Level build(EntityManager em) {
            Level level = build();
            em.persist(level);
            level.getSkills().forEach(em::persist);
            return level;
        }

        public Level build() {
            Level level = new Level().name(name).dimension(dimension).dependsOn(dependsOn).requiredScore(requiredScore);
            dimension.addLevels(level);
            skills.forEach(level::addSkills);
            return level;
        }
    }

}
