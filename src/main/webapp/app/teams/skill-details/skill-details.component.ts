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
import { IBadge } from 'app/shared/model/badge.model';
import { ITeamSkill } from 'app/shared/model/team-skill.model';

@Component({
    selector: 'jhi-skill-details',
    templateUrl: './skill-details.component.html',
    styleUrls: ['./skill-details.scss']
})
export class SkillDetailsComponent implements OnInit {
    team: ITeam;

    teams: ITeam[];

    skill: ISkill;

    teamSkills: ITeamSkill[] = [];

    badges: IBadge[] = [];

    skills: ISkill[] = [];

    achievableSkill: IAchievableSkill;

    selectedTeam: ITeam;

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
        this.route.data.subscribe(({ team, teams, skill, comments, selectedTeam, teamSkills, badges, skills }) => {
            this.team = team.body ? team.body : team;
            this.teams = teams.body ? teams.body : teams;
            this.skill = skill.body ? skill.body : skill;
            this.selectedTeam =
                selectedTeam === null || selectedTeam === 'undefined' ? null : selectedTeam.body ? selectedTeam.body : selectedTeam;
            this.teamSkills = (teamSkills && teamSkills.body ? teamSkills.body : teamSkills) || [];
            this.badges = (badges && badges.body ? badges.body : badges) || [];
            this.skills = (skills && skills.body ? skills.body : skills) || [];
            this._comments = comments.body ? comments.body : comments;
            this._mapCommentAuthors();
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
        this.skillComments = this._getSkillComments();
    }

    onCommentSubmitted(newComment: IComment) {
        if (newComment) {
            this._comments.push(newComment);
            this._mapCommentAuthors();
            this.skillComments = this._getSkillComments();
        }
    }

    onVoteSubmitted(voteObjs) {
        this.onCommentSubmitted(voteObjs.comment);
    }

    private _mapCommentAuthors() {
        (this._comments || [])
            .filter((comment: IComment) => comment.author === undefined || Object.keys(comment.author).length === 0)
            .forEach((comment: IComment) => {
                comment.author = (this.teams || []).find((t: ITeam) => t.id === comment.teamId) || {};
            });
    }

    private _getSkillComments(): IComment[] {
        return (this._comments || [])
            .filter(comment => comment.skillId === this.achievableSkill.skillId)
            .sort((comment1, comment2) => comment1.creationDate.diff(comment2.creationDate));
    }

    get currentTeam() {
        return this.teamsSelectionService.selectedTeam;
    }
}
