import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { TeamsSelectionService } from 'app/shared/teams-selection/teams-selection.service';
import { Injectable } from '@angular/core';

@Injectable()
export class TeamsSelectionResolve implements Resolve<any> {
    constructor(private teamsSelectionService: TeamsSelectionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.teamsSelectionService.query();
    }
}
