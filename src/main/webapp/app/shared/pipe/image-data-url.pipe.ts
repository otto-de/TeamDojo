import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'imageDataUrl' })
export class ImageDataUrlPipe implements PipeTransform {
    transform(imageData: string, mediaType: string): string {
        return imageData && mediaType ? `data:${mediaType};base64,${imageData}` : '';
    }
}
