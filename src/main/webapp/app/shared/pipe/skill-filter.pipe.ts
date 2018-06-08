import { Pipe, PipeTransform } from '@angular/core';
import { ILevelSkill } from 'app/shared/model/level-skill.model';
import { IBadgeSkill } from 'app/shared/model/badge-skill.model';

@Pipe({ name: 'skillFilter' })
export class SkillFilterPipe implements PipeTransform {
    transform(skills, searchString: string) {
        return searchString
            ? (skills || []).filter(skill => {
                  return (skill.title || skill.skillTitle || '').toLowerCase().includes(searchString.toLowerCase());
              })
            : skills;
    }
}
