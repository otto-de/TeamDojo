import { Pipe, PipeTransform } from '@angular/core';
import { ISkill } from 'app/shared/model/skill.model';
import { IBadge } from 'app/shared/model/badge.model';

@Pipe({ name: 'SkillFilter' })
export class SkillFilterPipe implements PipeTransform {
    transform(skills: ISkill[], searchString: string): ISkill[] {
        return searchString.length > 2
            ? skills.filter(skill => {
                  return (
                      skill.title.includes(searchString) ||
                      skill.description.includes(searchString) ||
                      skill.implementation.includes(searchString) ||
                      skill.validation.includes(searchString)
                  );
              })
            : skills;
    }
}
