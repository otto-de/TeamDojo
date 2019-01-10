import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'skillSort' })
export class SkillSortPipe implements PipeTransform {
    transform(skills = [], sortProperty) {
        const sortPropertyNullSafe = this._defaultString(sortProperty);
        const reverse = ['rateScore', 'rateCount'].includes(sortPropertyNullSafe) ? -1 : 1;
        return sortProperty
            ? Array.from(skills).sort((skill1, skill2) => {
                  return (
                      reverse *
                      this._defaultString(skill1[sortPropertyNullSafe]).localeCompare(this._defaultString(skill2[sortPropertyNullSafe]))
                  );
              })
            : skills;
    }

    _defaultString(smth) {
        return smth || smth === 0 ? String(smth) : String();
    }
}
