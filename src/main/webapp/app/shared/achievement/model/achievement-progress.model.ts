import { IProgress } from 'app/shared/achievement/model/progress.model';

export interface IAchievementProgress {
    countProgress?: IProgress;
    scoreProgress?: IProgress;
}

export class AchievementProgress implements IAchievementProgress {
    constructor(public countProgress?: IProgress, public scoreProgress?: IProgress) {}
}
