import { HttpParams } from '@angular/common/http';

export const createRequestOption = (req?: any): HttpParams => {
    let options: HttpParams = new HttpParams();
    if (req) {
        Object.keys(req).forEach(key => {
            if (key !== 'sort') {
                if (req[key] !== null && typeof req[key] !== 'undefined') {
                    options = options.set(key, req[key]);
                }
            }
        });
        if (req.sort) {
            req.sort.forEach(val => {
                if (val !== null && typeof val !== 'undefined') {
                    options = options.append('sort', val);
                }
            });
        }
    }
    return options;
};
