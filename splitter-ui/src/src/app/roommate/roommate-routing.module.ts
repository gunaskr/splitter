import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Shell } from '@app/shell/shell.service';
import { MyRoommatesComponent } from './my-roommates/my-roommates.component';


const routes: Routes = [
  Shell.childRoutes([{ path: 'roommate', component: MyRoommatesComponent, data: { title: 'My Roommates'} }]),
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RoommateRoutingModule { }
