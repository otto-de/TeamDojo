export interface IBreadcrumb {
    text?: string;
    path?: string;
    active?: boolean;
}

export class Breadcrumb implements IBreadcrumb {
    constructor(public text?: string, public path?: string, public active?: boolean) {}
}
