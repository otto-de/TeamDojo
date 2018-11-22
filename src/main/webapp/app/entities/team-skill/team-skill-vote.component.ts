import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITeamSkill } from 'app/shared/model/team-skill.model';
import { TeamSkillService } from 'app/entities/team-skill/team-skill.service';

@Component({
    selector: 'jhi-team-skill-vote',
    templateUrl: './team-skill-vote.component.html'
})
export class TeamSkillVoteComponent implements OnInit {
    teamSkill: ITeamSkill;
    disabled: boolean = false;

    constructor(private route: ActivatedRoute, private teamSkillService: TeamSkillService) {}

    ngOnInit() {
        this.route.data.subscribe(({ teamSkill }) => {
            this.teamSkill = teamSkill.body ? teamSkill.body : teamSkill;
            if (this.teamSkill.verifiedAt && this.teamSkill.verifiedAt !== undefined && this.teamSkill.verifiedAt !== null) {
                this.disabled = true;
            }
        });
    }
    upVote() {
        console.log('Upvote TeamSkill');
        this.teamSkill.vote = this.teamSkill.vote + 1;
        this.teamSkillService.update(this.teamSkill).subscribe(response => (this.teamSkill = response.body));
    }
    downVote() {
        console.log('downvote TeamSkill');
        this.teamSkill.vote = this.teamSkill.vote - 1;
        this.teamSkillService.update(this.teamSkill).subscribe(response => (this.teamSkill = response.body));
    }
}
