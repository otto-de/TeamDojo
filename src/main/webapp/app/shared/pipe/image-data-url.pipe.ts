import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Pipe({ name: 'imageDataUrl' })
export class ImageDataUrlPipe implements PipeTransform {
    constructor(private sanitizer: DomSanitizer) {}

    transform(imageData: string, mediaType: string): any {
        return imageData && mediaType ? `data:${mediaType};base64,${imageData}` : this.sanitizer.bypassSecurityTrustResourceUrl('');
    }
}
