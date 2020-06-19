package de.otto.teamdojo.repository;

import com.google.common.collect.Lists;
import de.otto.teamdojo.TeamdojoApp;
import de.otto.teamdojo.domain.Badge;
import de.otto.teamdojo.domain.Dimension;
import de.otto.teamdojo.domain.Level;
import de.otto.teamdojo.domain.Skill;
import de.otto.teamdojo.domain.Team;
import de.otto.teamdojo.domain.TeamSkill;
import de.otto.teamdojo.domain.enumeration.SkillStatus;
import de.otto.teamdojo.service.dto.AchievableSkillDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static de.otto.teamdojo.test.util.BadgeTestDataProvider.alwaysUpToDate;
import static de.otto.teamdojo.test.util.BadgeTestDataProvider.awsReady;
import static de.otto.teamdojo.test.util.DimensionTestDataProvider.security;
import static de.otto.teamdojo.test.util.LevelTestDataProvider.orange;
import static de.otto.teamdojo.test.util.LevelTestDataProvider.yellow;
import static de.otto.teamdojo.test.util.SkillTestDataProvider.*;
import static de.otto.teamdojo.test.util.TeamTestDataProvider.ft1;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TeamdojoApp.class)
@Transactional
public class SkillRepositoryIntTest {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private EntityManager em;

    private Team team;
    private Skill inputValidation;
    private Skill softwareUpdates;
    private Skill strongPasswords;
    private Skill passwordManager;
    private Skill dockerized;
    private Skill evilUserStories;
    private Level yellow;
    private Level orange;
    private Dimension security;
    private TeamSkill inputValidationSkill;
    private TeamSkill softwareUpdatesSkill;
    private TeamSkill passwordManagerSkill;
    private Badge awsReady;
    private Badge alwaysUpToDate;

    @Before
    public void setup() {
        setupTestData();
    }

    @After
    public void tearDown() {
        team = null;
        inputValidation = null;
        softwareUpdates = null;
        strongPasswords = null;
        passwordManager = null;
        dockerized = null;
        evilUserStories = null;
        yellow = null;
        orange = null;
        security = null;
        inputValidationSkill = null;
        softwareUpdatesSkill = null;
        passwordManagerSkill = null;
        awsReady = null;
        alwaysUpToDate = null;
    }

    @Test
    @Transactional
    public void getAllSkills() {

        List<Skill> allSkills = skillRepository.findAll();
        assertThat(allSkills).isNotNull();
        List<String> allTitles = allSkills.stream().map(Skill::getTitle).collect(Collectors.toList());
        assertThat(allTitles.size()).isEqualTo(6);
        assertThat(allTitles).containsExactlyInAnyOrder(
            INPUT_VALIDATION_TITLE, SOFTWARE_UPDATES_TITLE, STRONG_PASSWORDS_TITLE, PASSWORD_MANAGER_TITLE,
            DOCKERIZED_TITLE, EVIL_USER_STORIES_TITLE);

    }

    @Test
    @Transactional
    public void getCompleteAndIncompleteSkillsForAllLevelsAndBadges() {

        Long teamId = team.getId();

        List<Long> levelIds = Lists.newArrayList(yellow.getId(), orange.getId());
        List<Long> badgeIds = Lists.newArrayList(awsReady.getId(), alwaysUpToDate.getId());
        List<String> filter = Lists.newArrayList("COMPLETE", "INCOMPLETE");

        Page<AchievableSkillDTO> results = skillRepository.findAchievableSkillsByLevelsAndBadges(
            teamId, levelIds, badgeIds, filter, Pageable.unpaged());
        assertThat(results.getTotalElements()).isEqualTo(5);
        assertThat(results.map(AchievableSkillDTO::getTitle)).containsExactlyInAnyOrder(
            INPUT_VALIDATION_TITLE, SOFTWARE_UPDATES_TITLE, STRONG_PASSWORDS_TITLE, PASSWORD_MANAGER_TITLE,
            DOCKERIZED_TITLE);

        // Collect DTOs into Map
        Map<String, AchievableSkillDTO> resultMap =
            results.stream().collect(Collectors.toMap(AchievableSkillDTO::getTitle, Function.identity()));

        // Verify SkillStatus
        AchievableSkillDTO inputValidationDTO = resultMap.get(INPUT_VALIDATION_TITLE);
        assertThat(inputValidationDTO.getSkillStatus()).isSameAs(SkillStatus.ACHIEVED);

        AchievableSkillDTO softwareUpdatesDTO = resultMap.get(SOFTWARE_UPDATES_TITLE);
        assertThat(softwareUpdatesDTO.getSkillStatus()).isSameAs(SkillStatus.ACHIEVED);

        AchievableSkillDTO strongPasswordsDTO = resultMap.get(STRONG_PASSWORDS_TITLE);
        assertThat(strongPasswordsDTO.getSkillStatus()).isSameAs(SkillStatus.OPEN);

        AchievableSkillDTO passwordManagerDTO = resultMap.get(PASSWORD_MANAGER_TITLE);
        assertThat(passwordManagerDTO.getSkillStatus()).isSameAs(SkillStatus.EXPIRED);

        AchievableSkillDTO dockerizedDTO = resultMap.get(DOCKERIZED_TITLE);
        assertThat(dockerizedDTO.getSkillStatus()).isSameAs(SkillStatus.OPEN);

    }

    @Test
    @Transactional
    public void getCompleteAndIncompleteSkillsForLevelYellow() {

        Long teamId = team.getId();

        List<Long> levelIds = Lists.newArrayList(yellow.getId());
        List<String> filter = Lists.newArrayList("COMPLETE", "INCOMPLETE");

        Page<AchievableSkillDTO> results = skillRepository.findAchievableSkillsByLevels(
            teamId, levelIds, filter, Pageable.unpaged());
        assertThat(results.getTotalElements()).isEqualTo(2);
        assertThat(results.map(AchievableSkillDTO::getTitle)).containsExactlyInAnyOrder(
            INPUT_VALIDATION_TITLE, SOFTWARE_UPDATES_TITLE);
    }

    @Test
    @Transactional
    public void getCompleteAndIncompleteSkillsForBadgeAwsReady() {

        Long teamId = team.getId();

        List<Long> badgeIds = Lists.newArrayList(awsReady.getId());
        List<String> filter = Lists.newArrayList("COMPLETE", "INCOMPLETE");

        Page<AchievableSkillDTO> results = skillRepository.findAchievableSkillsByBadges(
            teamId, badgeIds, filter, Pageable.unpaged());
        assertThat(results.getTotalElements()).isEqualTo(2);
        assertThat(results.map(AchievableSkillDTO::getTitle)).containsExactlyInAnyOrder(
            INPUT_VALIDATION_TITLE, DOCKERIZED_TITLE);
    }

    @Test
    @Transactional
    public void getCompleteAndIncompleteSkillsForLevelOrangeAndBadgeAlwaysUpToDate() {

        Long teamId = team.getId();

        List<Long> levelIds = Lists.newArrayList(orange.getId());
        List<Long> badgeIds = Lists.newArrayList(alwaysUpToDate.getId());
        List<String> filter = Lists.newArrayList("COMPLETE", "INCOMPLETE");

        Page<AchievableSkillDTO> results = skillRepository.findAchievableSkillsByLevelsAndBadges(
            teamId, levelIds, badgeIds, filter, Pageable.unpaged());
        assertThat(results.getTotalElements()).isEqualTo(3);
        assertThat(results.map(AchievableSkillDTO::getTitle)).containsExactlyInAnyOrder(
            SOFTWARE_UPDATES_TITLE, STRONG_PASSWORDS_TITLE, PASSWORD_MANAGER_TITLE);
    }

    @Test
    @Transactional
    public void getIncompleteSkills() {

        Long teamId = team.getId();

        List<Long> levelIds = Lists.newArrayList(yellow.getId(), orange.getId());
        List<Long> badgeIds = Lists.newArrayList(awsReady.getId());
        List<String> filter = Lists.newArrayList("INCOMPLETE");

        Page<AchievableSkillDTO> results = skillRepository.findAchievableSkillsByLevelsAndBadges(
            teamId, levelIds, badgeIds, filter, Pageable.unpaged());
        assertThat(results.getTotalElements()).isEqualTo(2);
        assertThat(results.map(AchievableSkillDTO::getTitle)).containsExactlyInAnyOrder(
            STRONG_PASSWORDS_TITLE, DOCKERIZED_TITLE);
    }

    @Test
    @Transactional
    public void getCompleteSkills() {

        Long teamId = team.getId();

        List<Long> levelIds = Lists.newArrayList(yellow.getId(), orange.getId());
        List<Long> badgeIds = Lists.newArrayList(awsReady.getId());
        List<String> filter = Lists.newArrayList("COMPLETE");

        Page<AchievableSkillDTO> results = skillRepository.findAchievableSkillsByLevelsAndBadges(
            teamId, levelIds, badgeIds, filter, Pageable.unpaged());
        assertThat(results.getTotalElements()).isEqualTo(3);
        assertThat(results.map(AchievableSkillDTO::getTitle)).containsExactlyInAnyOrder(
            INPUT_VALIDATION_TITLE, SOFTWARE_UPDATES_TITLE, PASSWORD_MANAGER_TITLE);
    }

    private void setupTestData() {
        //Skills
        inputValidation = inputValidation().build(em);
        softwareUpdates = softwareUpdates().build(em);
        strongPasswords = strongPasswords().build(em);
        passwordManager = passwordManager().build(em);
        dockerized = dockerized().build(em);
        evilUserStories = evilUserStories().build(em);

        //Dimension
        security = security().build(em);

        //Level
        yellow = yellow(security).addSkill(inputValidation).addSkill(softwareUpdates).build(em);
        orange = orange(security).addSkill(strongPasswords).addSkill(passwordManager).dependsOn(yellow).build(em);

        //Badges
        awsReady = awsReady().addDimension(security).addSkill(inputValidation).addSkill(dockerized).build(em);
        alwaysUpToDate = alwaysUpToDate().addSkill(softwareUpdates).build(em);

        team = ft1().build(em);
        team.addParticipations(security);
        em.persist(team);

        inputValidationSkill = new TeamSkill();
        inputValidationSkill.setTeam(team);
        inputValidationSkill.setCompletedAt((Instant.now().minus(20, ChronoUnit.DAYS)));
        inputValidationSkill.setSkill(inputValidation);
        em.persist(inputValidationSkill);
        team.addSkills(inputValidationSkill);

        softwareUpdatesSkill = new TeamSkill();
        softwareUpdatesSkill.setTeam(team);
        softwareUpdatesSkill.setCompletedAt((Instant.now().minus(120, ChronoUnit.DAYS)));
        softwareUpdatesSkill.setSkill(softwareUpdates);
        em.persist(softwareUpdatesSkill);
        team.addSkills(softwareUpdatesSkill);

        passwordManagerSkill = new TeamSkill();
        passwordManagerSkill.setTeam(team);
        passwordManagerSkill.setCompletedAt((Instant.now().minus(120, ChronoUnit.DAYS)));
        passwordManagerSkill.setSkill(passwordManager);
        em.persist(passwordManagerSkill);
        team.addSkills(passwordManagerSkill);

        em.persist(team);
    }
}
