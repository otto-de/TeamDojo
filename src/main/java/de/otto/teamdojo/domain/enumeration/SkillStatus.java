package de.otto.teamdojo.domain.enumeration;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Enumeration of all possible status values which a {@link de.otto.teamdojo.service.dto.TeamSkillDTO} or an
 * {@link de.otto.teamdojo.service.dto.AchievableSkillDTO} may have. The status is <b>not</b> a persistent value
 * but is computed on-the-fly at runtime.
 */
public enum SkillStatus {

    /**
     * The skill has never been achieved by the team.
     */
    OPEN,

    /**
     * The skill has been achieved by the team and is neither expired nor about to expire.
     */
    ACHIEVED,

    /**
     * The skill has been achieved by the team, the expiry date has not been reached, but is about to be reached.
     */
    EXPIRING,

    /**
     * The skill has been achieved by the team, but the expiry date has passed (without a renewal of the skill).
     */
    EXPIRED,

    /**
     * The skill has been marked irrelevant for the team.
     */
    IRRELEVANT;

    public static SkillStatus determineSkillStatus(Boolean irrelevant, Instant completedAt, Integer skillExpiryPeriod) {
        if (irrelevant != null && irrelevant) {
            return SkillStatus.IRRELEVANT;
        } else if (completedAt == null) {
            return SkillStatus.OPEN;
        } else if (skillExpiryPeriod == null) {
            return SkillStatus.ACHIEVED;
        } else {
            Instant now = Instant.now();
            Instant expiration = completedAt.plus(skillExpiryPeriod.intValue(), ChronoUnit.DAYS);
            Instant expirationWarningEnds = expiration.plus(7, ChronoUnit.DAYS);
            if (now.isBefore(expiration)) {
                return SkillStatus.ACHIEVED;
            } else if (now.isBefore(expirationWarningEnds)) {
                return SkillStatus.EXPIRING;
            } else {
                return SkillStatus.EXPIRED;
            }
        }
    }


}
