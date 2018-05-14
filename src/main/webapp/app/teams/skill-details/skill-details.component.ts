import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ITeam } from 'app/shared/model/team.model';
import { ISkill } from 'app/shared/model/skill.model';

@Component({
    selector: 'jhi-skill-details',
    templateUrl: './skill-details.component.html',
    styleUrls: ['./skill-details.scss']
})
export class SkillDetailsComponent implements OnInit {
    private team: ITeam;

    private skill: ISkill;

    constructor(private route: ActivatedRoute) {}

    ngOnInit(): void {
        this.route.data.subscribe(({ team, skill }) => {
            this.team = team.body[0] ? team.body[0] : team;
            this.skill = skill;
        });
    }

    handleSkillChange(skill: ISkill) {
        this.skill = skill;
    }
}
