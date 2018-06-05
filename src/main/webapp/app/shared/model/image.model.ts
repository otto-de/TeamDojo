export interface IImage {
    id?: number;
    smallContentType?: string;
    small?: any;
    mediumContentType?: string;
    medium?: any;
    largeContentType?: string;
    large?: any;
}

export class Image implements IImage {
    constructor(
        public id?: number,
        public smallContentType?: string,
        public small?: any,
        public mediumContentType?: string,
        public medium?: any,
        public largeContentType?: string,
        public large?: any
    ) {}
}
