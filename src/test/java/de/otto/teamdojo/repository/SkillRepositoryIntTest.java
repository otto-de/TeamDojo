package de.otto.teamdojo.repository;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

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
    public void getAllAchievableSkills() throws Exception {
        setupTestData();
        em.flush();

        Long teamId = team.getId();

        List<Long> levelIds = new ArrayList<Long>();
        levelIds.add(yellow.getId());
        levelIds.add(orange.getId());

        List<Long> badgeIds = new ArrayList<Long>();
        badgeIds.add(awsReady.getId());

        List<String> filter = new ArrayList<String>();
        filter.add("INCOMPLETED");

        Page<AchievableSkillDTO> results = skillRepository.findAchievableSkill(teamId, levelIds, badgeIds, filter, null);
        System.out.println("Results: " + results.getTotalElements());

        //assertThat(results.getTotalElements().isEqualTo(null));

    }


    private void setupTestData() {
        inputValidation = inputValidation().build(em);
        softwareUpdates = softwareUpdates().build(em);
        strongPasswords = strongPasswords().build(em);
        dockerized = dockerized().build(em);
        Skill evilUserStories_notAchievable = evilUserStories().build(em);

        security = security().build(em);

        yellow = yellow(security).addSkill(inputValidation).addSkill(softwareUpdates).build(em);
        orange = orange(security).addSkill(strongPasswords).dependsOn(yellow).build(em);

        awsReady = awsReady().addDimension(security).addSkill(inputValidation).addSkill(dockerized).build(em);
        alwaysUpToDate = alwaysUpToDate().addSkill(softwareUpdates).build(em);

        team = ft1().build(em);
        team.addParticipations(security);
        em.persist(team);
        teamSkill = new TeamSkill();
        teamSkill.setTeam(team);
        teamSkill.setSkill(inputValidation);
        em.persist(teamSkill);
        team.addSkills(teamSkill);
        em.persist(team);
    }

}
