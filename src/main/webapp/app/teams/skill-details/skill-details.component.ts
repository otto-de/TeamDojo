import { Component, OnInit } from '@angular/core';

import { SkillDetailsComponentParent } from 'app/shared/skill-details/skill-details.component';
import { TeamsSkillsService } from 'app/teams/teams-skills.service';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'jhi-skill-details',
    templateUrl: './skill-details.component.html',
    styleUrls: ['./skill-details.scss']
})
export class SkillDetailsComponent extends SkillDetailsComponentParent implements OnInit {
    constructor(route: ActivatedRoute, teamsSkillsService: TeamsSkillsService) {
        super(route, teamsSkillsService);
    }

    ngOnInit(): void {
        this.route.data.subscribe((resolvedData: { team; teams; skill; comments; selectedTeam; badges; skills }) => {
            this.team = resolvedData.team && resolvedData.team.body ? resolvedData.team.body : resolvedData.team;
            super.setResolvedData(resolvedData);
        });
        super.loadData();
    }

    onVoteSubmitted(voteObjs) {
        this.onCommentSubmitted(voteObjs.comment);
        const skillRate = voteObjs.skillRate;

        for (const skill of this.skills) {
            if (skillRate.skillId === skill.id) {
                skill.rateScore = skillRate.rateScore;
                skill.rateCount = skillRate.rateCount;
            }
        }
    }
}
