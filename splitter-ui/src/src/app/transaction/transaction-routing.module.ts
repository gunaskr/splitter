import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TransactionComponent } from './transaction.component';
import { Shell } from '@app/shell/shell.service';


const routes: Routes = [
  Shell.childRoutes([{ path: 'transaction', component: TransactionComponent, data: { title: 'Add New Transactions'} }]),
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TransactionRoutingModule { }
