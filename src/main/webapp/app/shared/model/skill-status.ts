export const enum SkillStatus {
    OPEN = 'OPEN',
    ACHIEVED = 'ACHIEVED',
    EXPIRING = 'EXPIRING',
    EXPIRED = 'EXPIRED',
    IRRELEVANT = 'IRRELEVANT'
}

export class SkillStatusUtils {
    public static isValid(skillStatus: SkillStatus): boolean {
        return skillStatus === SkillStatus.ACHIEVED || skillStatus === SkillStatus.EXPIRING;
    }

    public static isInvalid(skillStatus: SkillStatus): boolean {
        return skillStatus === SkillStatus.OPEN || skillStatus === SkillStatus.EXPIRED;
    }

    public static getStyleClassName(skillStatus: SkillStatus): string {
        return skillStatus ? skillStatus.toLowerCase() : '';
    }
}
