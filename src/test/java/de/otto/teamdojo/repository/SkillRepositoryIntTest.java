package de.otto.teamdojo.repository;

import com.google.common.collect.Lists;
import de.otto.teamdojo.TeamdojoApp;
import de.otto.teamdojo.domain.*;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static de.otto.teamdojo.test.util.BadgeTestDataProvider.alwaysUpToDate;
import static de.otto.teamdojo.test.util.BadgeTestDataProvider.awsReady;
import static de.otto.teamdojo.test.util.DimensionTestDataProvider.security;
import static de.otto.teamdojo.test.util.LevelTestDataProvider.orange;
import static de.otto.teamdojo.test.util.LevelTestDataProvider.yellow;
import static de.otto.teamdojo.test.util.SkillTestDataProvider.*;
import static de.otto.teamdojo.test.util.TeamTestDataProvider.ft1;

/**
 * Test class for the CustomAuditEventRepository class.
 *
 * @see CustomAuditEventRepository
 */
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
    private Skill dockerized;
    private Level yellow;
    private Level orange;
    private Dimension security;
    private TeamSkill teamSkill;
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
        dockerized = null;
        yellow = null;
        orange = null;
        security = null;
        teamSkill = null;
        awsReady = null;
        alwaysUpToDate = null;
    }

    @Test
    @Transactional
    public void getCompleteAndIncompleteSkills() throws Exception {
        setupTestData();

        Skill evilUserStories_notAchievable = evilUserStories().build(em);

        em.flush();

        Long teamId = team.getId();

        List<Long> levelIds = Lists.newArrayList(yellow.getId(), orange.getId());

        List<Long> badgeIds = Lists.newArrayList(awsReady.getId());

        List<String> filter = Lists.newArrayList("COMPLETE", "INCOMPLETE");

        Page<AchievableSkillDTO> results = skillRepository.findAchievableSkillsByLevelsAndBadges(teamId, levelIds, badgeIds, filter, Pageable.unpaged());
        assertThat(results.getTotalElements()).isEqualTo(4);
    }

    @Test
    @Transactional
    public void getIncompleteSkills() throws Exception {
        setupTestData();
        em.flush();

        Long teamId = team.getId();

        List<Long> levelIds = new ArrayList<Long>();
        levelIds.add(yellow.getId());
        levelIds.add(orange.getId());

        List<Long> badgeIds = new ArrayList<Long>();
        badgeIds.add(awsReady.getId());

        List<String> filter = new ArrayList<String>();
        filter.add("INCOMPLETE");

        Page<AchievableSkillDTO> results = skillRepository.findAchievableSkillsByLevelsAndBadges(teamId, levelIds, badgeIds, filter, Pageable.unpaged());
        assertThat(results.getTotalElements()).isEqualTo(3);
    }

    @Test
    @Transactional
    public void getCompleteSkills() throws Exception {
        setupTestData();

        Skill evilUserStories_notAchievable = evilUserStories().build(em);

        em.flush();

        Long teamId = team.getId();

        List<Long> levelIds = new ArrayList<Long>();
        levelIds.add(yellow.getId());
        levelIds.add(orange.getId());

        List<Long> badgeIds = new ArrayList<Long>();
        badgeIds.add(awsReady.getId());

        List<String> filter = new ArrayList<String>();
        filter.add("COMPLETE");

        Page<AchievableSkillDTO> results = skillRepository.findAchievableSkillsByLevelsAndBadges(teamId, levelIds, badgeIds, filter, Pageable.unpaged());
        assertThat(results.getTotalElements()).isEqualTo(1);
    }


    private void setupTestData() {
        //Skills
        inputValidation = inputValidation().build(em);
        softwareUpdates = softwareUpdates().build(em);
        strongPasswords = strongPasswords().build(em);
        dockerized = dockerized().build(em);
        Skill evilUserStories_notAchievable = evilUserStories().build(em);

        //Dimension
        security = security().build(em);

        //Level
        yellow = yellow(security).addSkill(inputValidation).addSkill(softwareUpdates).build(em);
        orange = orange(security).addSkill(strongPasswords).dependsOn(yellow).build(em);

        //Badges
        awsReady = awsReady().addDimension(security).addSkill(inputValidation).addSkill(dockerized).build(em);
        alwaysUpToDate = alwaysUpToDate().addSkill(softwareUpdates).build(em);

        team = ft1().build(em);
        team.addParticipations(security);
        em.persist(team);

        teamSkill = new TeamSkill();
        teamSkill.setTeam(team);
        teamSkill.setCompletedAt((Instant.now()));
        teamSkill.setSkill(inputValidation);
        em.persist(teamSkill);
        team.addSkills(teamSkill);
        em.persist(team);
    }

}
