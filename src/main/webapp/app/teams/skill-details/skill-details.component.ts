import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ITeam } from 'app/shared/model/team.model';
import { ISkill } from 'app/shared/model/skill.model';
import { TeamSkillService } from 'app/entities/team-skill';
import { AchievableSkill, IAchievableSkill } from 'app/shared/model/achievable-skill.model';
import { TeamsSkillsService } from 'app/teams/teams-skills.service';
import { TeamsSkillsComponent } from 'app/teams/teams-skills.component';
import { SkillDetailsInfoComponent } from 'app/teams/skill-details/skill-details-info/skill-details-info.component';
import { TeamsSelectionService } from 'app/teams/teams-selection/teams-selection.service';
import { IComment } from 'app/shared/model/comment.model';

@Component({
    selector: 'jhi-skill-details',
    templateUrl: './skill-details.component.html',
    styleUrls: ['./skill-details.scss']
})
export class SkillDetailsComponent implements OnInit {
    team: ITeam;

    skill: ISkill;

    achievableSkill: IAchievableSkill;

    private _comments: IComment[];

    skillComments: IComment[];

    @Output() onSkillChanged = new EventEmitter<IAchievableSkill>();

    @ViewChild(TeamsSkillsComponent) skillList;

    @ViewChild(SkillDetailsInfoComponent) skillInfo;

    constructor(
        private route: ActivatedRoute,
        private teamSkillService: TeamSkillService,
        private teamsSkillsService: TeamsSkillsService,
        private teamsSelectionService: TeamsSelectionService
    ) {}

    ngOnInit(): void {
        this.route.data.subscribe(({ team, skill, comments }) => {
            this.team = team;
            this.skill = skill;
            this._comments = comments.body ? comments.body : comments;
        });
        this.loadData();
    }

    loadData() {
        this.achievableSkill = new AchievableSkill();
        this.achievableSkill.skillId = this.skill.id;
        this.teamsSkillsService.findAchievableSkill(this.team.id, this.skill.id).subscribe(skill => {
            this.achievableSkill = skill;
            this.skillComments = this._getSkillComments();
        });
    }

    onSkillInListChanged(skillObjs) {
        this.skillInfo.onSkillInListChanged(skillObjs);
    }

    onSkillInListClicked(skillObjs) {
        this.achievableSkill = skillObjs.aSkill;
        this.skillInfo.onSkillInListClicked(skillObjs);
    }

    onCommentSubmitted(newComment: IComment) {
        if (newComment) {
            this._comments.push(newComment);
            this.skillComments = this._getSkillComments();
        }
    }

    private _getSkillComments(): IComment[] {
        return (this._comments || [])
            .filter((comment: IComment) => comment.skillId === this.achievableSkill.skillId)
            .sort((comment1: IComment, comment2: IComment) => comment1.creationDate.diff(comment2.creationDate));
    }

    get currentTeam() {
        return this.teamsSelectionService.selectedTeam;
    }
}
