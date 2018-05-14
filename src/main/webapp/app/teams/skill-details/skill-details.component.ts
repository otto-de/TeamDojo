import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ITeam } from 'app/shared/model/team.model';
import { ISkill } from 'app/shared/model/skill.model';
import { TeamSkillService } from 'app/entities/team-skill';
import { TeamsService } from 'app/teams/teams.service';
import { IBadge } from 'app/shared/model/badge.model';
import { ILevel } from 'app/shared/model/level.model';
import { LevelService } from 'app/entities/level';
import { BadgeService } from 'app/entities/badge';

@Component({
    selector: 'jhi-skill-details',
    templateUrl: './skill-details.component.html',
    styleUrls: ['./skill-details.scss']
})
export class SkillDetailsComponent implements OnInit {
    private team: ITeam;

    private skill: ISkill;

    private skillAchieved = false;

    private achievedByTeams: ITeam[] = [];

    private neededForLevels: ILevel[] = [];

    private neededForBadges: IBadge[] = [];

    constructor(
        private route: ActivatedRoute,
        private teamSkillService: TeamSkillService,
        private teamsService: TeamsService,
        private levelService: LevelService,
        private badgeService: BadgeService
    ) {}

    ngOnInit(): void {
        this.route.data.subscribe(({ team, skill }) => {
            this.team = team.body[0] ? team.body[0] : team;
            this.skill = skill;
        });
        this.loadData();
    }

    loadData() {
        this.achievedByTeams = [];
        this.neededForLevels = [];
        this.neededForBadges = [];

        this.teamSkillService.query({ 'skillId.equals': this.skill.id, 'completedAt.specified': true }).subscribe(res => {
            const teamsId = res.body.map(ts => ts.teamId);
            if (teamsId.length !== 0) {
                this.teamsService.query({ 'id.in': teamsId }).subscribe(teamsRes => {
                    this.achievedByTeams = teamsRes.body;
                });
            }
        });

        this.teamSkillService.query({ 'skillId.equals': this.skill.id, 'teamId.equals': this.team.id }).subscribe(res => {
            if (res.body.length !== 0) {
                this.skillAchieved = !!res.body[0].completedAt;
            } else {
                this.skillAchieved = false;
            }
        });

        this.levelService.query({ 'skillId.in': this.skill.id }).subscribe(res => {
            this.neededForLevels = res.body;
        });

        this.badgeService.query({ 'skillId.in': this.skill.id }).subscribe(res => {
            this.neededForBadges = res.body;
        });
    }

    handleSkillChange(skill: ISkill) {
        this.skill = skill;
        this.loadData();
    }

    getTeamImage(team: ITeam) {
        return team.picture && team.pictureContentType ? `data:${team.pictureContentType};base64,${team.picture}` : '';
    }

    getLevelImage(level: ILevel) {
        return level.picture && level.pictureContentType ? `data:${level.pictureContentType};base64,${level.picture}` : '';
    }

    getBadgeImage(badge: IBadge) {
        return badge.logo && badge.logoContentType ? `data:${badge.logoContentType};base64,${badge.logo}` : '';
    }
}
