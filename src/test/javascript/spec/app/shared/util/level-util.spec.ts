import { Level } from 'app/shared/model/level.model';
import Util from '../../../helpers/Util.service';
import { sortLevels } from 'app/shared';

describe('Util Tests', () => {
    describe('LevelUtil', () => {
        const buildEntity = Util.wrap;

        it('Should sort multiple levels descendingly', () => {
            // GIVEN
            const levels = [
                buildEntity(new Level(126), { dimensionId: 122, dependsOnId: 125 }),
                buildEntity(new Level(125), { dimensionId: 122, dependsOnId: 124 }),
                buildEntity(new Level(123), { dimensionId: 122, dependsOnId: undefined }),
                buildEntity(new Level(124), { dimensionId: 122, dependsOnId: 123 })
            ];
            // WHEN
            const result = sortLevels(levels);

            // THEN

            expect(result.length).toEqual(4);
            expect(result[0].id).toEqual(126);
            expect(result[1].id).toEqual(125);
            expect(result[2].id).toEqual(124);
            expect(result[3].id).toEqual(123);
        });

        it('Should sort multiple levels without a root level descendingly', () => {
            // GIVEN
            const levels = [
                buildEntity(new Level(124), { dimensionId: 122, dependsOnId: 123 }),
                buildEntity(new Level(125), { dimensionId: 122, dependsOnId: 123 }),
                buildEntity(new Level(123), { dimensionId: 122, dependsOnId: 126 })
            ];

            // WHEN
            const result = sortLevels(levels);

            // THEN
            expect(result.length).toEqual(3);
            // use incoming order if dependency is the same
            expect(result[0].id).toEqual(125);
            expect(result[1].id).toEqual(124);
            expect(result[2].id).toEqual(123);
        });
    });
});
