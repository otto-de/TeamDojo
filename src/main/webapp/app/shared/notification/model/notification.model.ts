import { IActivity } from 'app/shared/model/activity.model';

export interface INotification {
    activity?: IActivity;
    data?: any;
    unread?: boolean;
}

export class Notification implements INotification {
    public data?: any;

    constructor(public activity?: IActivity, unread?: boolean) {
        this.data = this.activity.data ? JSON.parse(this.activity.data) : null;
    }
}
