import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'imageDataUrl' })
export class ImageDataUrlPipe implements PipeTransform {
    transform(imageData: string, mediaType: string): string {
        const white = 'data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==';
        return imageData && mediaType ? `data:${mediaType};base64,${imageData}` : white;
    }
}
