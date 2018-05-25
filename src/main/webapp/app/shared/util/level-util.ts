import { ILevel } from 'app/shared/model/level.model';

export const sortLevels = (levels: ILevel[]) => {
    const sortedLevels: ILevel[] = [];
    if (levels && levels.length > 0) {
        const rootLevelIndex = levels.findIndex((level: ILevel) => level.dependsOnId === undefined || level.dependsOnId === null);
        sortedLevels.unshift(rootLevelIndex === -1 ? levels.pop() : levels.splice(rootLevelIndex, 1)[0]);
        while (levels.length > 0) {
            const nextLevelIndex = levels.findIndex((level: ILevel) => level.dependsOnId === sortedLevels[0].id);
            sortedLevels.unshift(nextLevelIndex === -1 ? levels.pop() : levels.splice(nextLevelIndex, 1)[0]);
        }
    }
    return sortedLevels;
};
