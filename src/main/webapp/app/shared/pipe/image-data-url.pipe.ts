import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'imageDataUrl' })
export class ImageDataUrlPipe implements PipeTransform {
    transform(imageId: string, size: string): string {
        return imageId ? `/api/images/${imageId}/content?size=${size}` : '';
    }
}
