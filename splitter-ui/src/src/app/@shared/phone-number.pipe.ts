import { Pipe, PipeTransform } from '@angular/core';
import { SlicePipe } from '@angular/common';

@Pipe({
  name: 'phoneNumber',
})
export class PhoneNumberPipe implements PipeTransform {
  transform(value: string, ...args: unknown[]): string {
    const slicePipe: SlicePipe = new SlicePipe();
    return (
      '((' +
      slicePipe.transform(value, 0, 3) +
      ')' +
      slicePipe.transform(value, 3, 6) +
      '-' +
      slicePipe.transform(value, 6, 9) +
      ')'
    );
  }
}
