import { ITeam } from 'app/shared/model/team.model';
import { IDimension } from 'app/shared/model/dimension.model';
import { IBadge } from 'app/shared/model/badge.model';
import { ILevel } from 'app/shared/model/level.model';

export class RelevanceCheck {
    constructor(private team: ITeam) {}

    public isRelevantDimensionId(dimensionId: number): boolean {
        return this.team.participations && this.team.participations.some((dimension: IDimension) => dimension.id === dimensionId);
    }

    public isRelevantDimension(dimension: IDimension): boolean {
        return dimension && this.isRelevantDimensionId(dimension.id);
    }

    public isRelevantLevel(level: ILevel): boolean {
        return level && this.isRelevantDimensionId(level.dimensionId);
    }

    public isRelevantBadge(badge: IBadge): boolean {
        return (
            badge &&
            (this.isDimensionlessBadge(badge) || badge.dimensions.some((dimension: IDimension) => this.isRelevantDimension(dimension)))
        );
    }

    public isRelevantLevelOrBadge(item: ILevel | IBadge): boolean {
        if ((<ILevel>item).dimensionId) {
            return this.isRelevantLevel(item);
        }
        return this.isRelevantBadge(<IBadge>item);
    }

    private isDimensionlessBadge(badge: IBadge): boolean {
        return !badge.dimensions || !badge.dimensions.length;
    }
}
