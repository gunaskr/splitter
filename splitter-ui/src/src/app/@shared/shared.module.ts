import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoaderComponent } from './loader/loader.component';
import { NgbdSortableHeader } from './sortable.directive';
import { PhoneNumberPipe } from './phone-number.pipe';

@NgModule({
  imports: [CommonModule],
  declarations: [LoaderComponent, NgbdSortableHeader, PhoneNumberPipe],
  exports: [LoaderComponent, PhoneNumberPipe],
})
export class SharedModule {}
