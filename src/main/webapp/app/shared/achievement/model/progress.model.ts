export interface IProgress {
    achieved?: number;
    required?: number;
    totalScore?: number;

    getPercentage(): number;

    isCompleted(): boolean;
}

export class Progress implements IProgress {
    constructor(public achieved?: number, public required?: number, public totalScore?: number) {}

    public getPercentage(): number {
        return this.required !== 0 ? this.achieved / this.totalScore * 100 : 100;
    }

    public isCompleted(): boolean {
        return this.achieved >= this.required;
    }
}
