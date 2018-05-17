import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'truncateString' })
export class TruncateStringPipe implements PipeTransform {
    transform(stringToTruncate: string, truncateLength = 10): string {
        let truncatedString = '';
        if (stringToTruncate) {
            if (stringToTruncate.length > truncateLength) {
                truncatedString = stringToTruncate.substring(0, truncateLength) + '...';
            } else {
                truncatedString = stringToTruncate;
            }
        }
        return truncatedString;
    }
}
