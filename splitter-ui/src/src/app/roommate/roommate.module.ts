import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RoommateRoutingModule } from './roommate-routing.module';
import { MyRoommatesComponent } from './my-roommates/my-roommates.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '@app/@shared';

@NgModule({
  imports: [CommonModule, RoommateRoutingModule, FormsModule, ReactiveFormsModule, SharedModule],
  declarations: [MyRoommatesComponent],
})
export class RoommateModule {}
