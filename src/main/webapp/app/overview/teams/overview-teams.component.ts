import { Component, Input, OnInit } from '@angular/core';
import { ITeam } from 'app/shared/model/team.model';
import { ILevel } from 'app/shared/model/level.model';
import { IBadge } from 'app/shared/model/badge.model';
import { CompletionCheck } from 'app/shared/util/completion-check';

@Component({
    selector: 'jhi-overview-teams',
    templateUrl: './overview-teams.component.html',
    styleUrls: ['./overview-teams.scss']
})
export class OverviewTeamsComponent implements OnInit {
    @Input() teams: ITeam[];
    @Input() levels: ILevel[];
    @Input() badges: IBadge[];

    ngOnInit(): void {}

    onTeamClicked(event, team: ITeam) {
        event.preventDefault();
    }

    calcLevelBase(team: ITeam) {
        const relevantDimensionIds = team.participations.map(d => d.id);
        return this.levels.filter(l => relevantDimensionIds.indexOf(l.dimensionId) !== -1).length;
    }

    calcCompletedLevel(team: ITeam) {
        let count = 0;
        team.participations.forEach(dimension => {
            for (const level of dimension.levels) {
                if (!this.isLevelOrBadgeCompleted(team, level)) {
                    break;
                }
                count++;
            }
        });
        return count;
    }

    calcCompletedBadges(team: ITeam) {
        let count = 0;
        this.badges.forEach(badge => {
            if (this.isLevelOrBadgeCompleted(team, badge)) {
                count++;
            }
        });
        return count;
    }

    private isLevelOrBadgeCompleted(team: ITeam, item: ILevel | IBadge): boolean {
        return new CompletionCheck(team, item).isCompleted();
    }
}
