export interface IProgress {
    achieved?: number;
    required?: number;

    getPercentage(): number;

    isCompleted(): boolean;
}

export class Progress implements IProgress {
    constructor(public achieved?: number, public required?: number) {}

    public getPercentage(): number {
        return this.required != 0 ? this.achieved / this.required * 100 : 100;
    }

    public isCompleted(): boolean {
        return this.achieved >= this.required;
    }
}
