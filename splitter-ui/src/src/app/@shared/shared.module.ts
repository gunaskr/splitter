import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoaderComponent } from './loader/loader.component';
import { NgbdSortableHeader } from './sortable.directive';

@NgModule({
  imports: [CommonModule],
  declarations: [LoaderComponent, NgbdSortableHeader],
  exports: [LoaderComponent],
})
export class SharedModule {}
