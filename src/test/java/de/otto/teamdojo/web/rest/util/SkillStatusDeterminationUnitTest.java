package de.otto.teamdojo.web.rest.util;

import de.otto.teamdojo.domain.enumeration.SkillStatus;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Unit-tests the logic that determines the Skill Status.
 */
public class SkillStatusDeterminationUnitTest {

    private static final Instant now = Instant.now();

    @Test
    public void testSkillStatusOPEN() {
        SkillStatus skillStatus = SkillStatus.determineSkillStatus(false, null, 14);
        Assert.assertTrue(skillStatus == SkillStatus.OPEN);
    }

    @Test
    public void testSkillStatusACHIEVED() {
        SkillStatus skillStatus = SkillStatus.determineSkillStatus(false, now.minus(2, ChronoUnit.DAYS), 14);
        Assert.assertTrue(skillStatus == SkillStatus.ACHIEVED);
    }

    @Test
    public void testSkillStatusACHIEVED_status_does_not_expire() {
        SkillStatus skillStatus = SkillStatus.determineSkillStatus(false, now.minus(2, ChronoUnit.DAYS), null);
        Assert.assertTrue(skillStatus == SkillStatus.ACHIEVED);
    }

    @Test
    public void testSkillStatusEXPIRED() {
        SkillStatus skillStatus = SkillStatus.determineSkillStatus(false, now.minus(22, ChronoUnit.DAYS), 14);
        Assert.assertTrue(skillStatus == SkillStatus.EXPIRED);
    }

    @Test
    public void testSkillStatusEXPIRING() {
        SkillStatus skillStatus = SkillStatus.determineSkillStatus(false, now.minus(18, ChronoUnit.DAYS), 14);
        Assert.assertTrue(skillStatus == SkillStatus.EXPIRING);
    }

    @Test
    public void testSkillStatusIRRELEVANT() {
        SkillStatus skillStatus = SkillStatus.determineSkillStatus(true, null, null);
        Assert.assertTrue(skillStatus == SkillStatus.IRRELEVANT);
    }

}
