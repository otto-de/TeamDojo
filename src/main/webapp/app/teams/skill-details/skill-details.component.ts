import { Component, OnInit } from '@angular/core';

import { SkillDetailsComponentParent } from 'app/shared/skill-details/skill-details.component';
import { TeamsSkillsService } from 'app/teams/teams-skills.service';
import { ActivatedRoute } from '@angular/router';
import { AchievableSkill, IAchievableSkill } from 'app/shared/model/achievable-skill.model';

@Component({
    selector: 'jhi-skill-details',
    templateUrl: './skill-details.component.html',
    styleUrls: ['./skill-details.scss']
})
export class SkillDetailsComponent extends SkillDetailsComponentParent implements OnInit {
    achievableSkill: IAchievableSkill;
    constructor(route: ActivatedRoute, teamsSkillsService: TeamsSkillsService) {
        super(route, teamsSkillsService);
    }

    ngOnInit(): void {
        this.route.data.subscribe((resolvedData: { team; teams; skill; comments; selectedTeam; badges; skills }) => {
            this.team = resolvedData.team && resolvedData.team.body ? resolvedData.team.body : resolvedData.team;
            super.setResolvedData(resolvedData);
        });
        this.loadData();
    }

    loadData() {
        this.achievableSkill = new AchievableSkill();
        this.achievableSkill.skillId = this.skill.id;
        this.teamsSkillsService.findAchievableSkill(this.team ? this.team.id : this.selectedTeam.id, this.skill.id).subscribe(skill => {
            this.achievableSkill = skill;
            this.skillComments = super._getSkillComments();
        });
    }

    onSkillInListClicked(skillObjs) {
        this.achievableSkill = skillObjs.aSkill;
        super.onSkillInListClicked(skillObjs);
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
