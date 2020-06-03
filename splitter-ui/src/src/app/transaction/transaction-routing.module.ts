import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TransactionComponent } from './transaction.component';
import { Shell } from '@app/shell/shell.service';
import { TransactionListComponent } from './transaction-list/transaction-list.component';


const routes: Routes = [
  Shell.childRoutes([
    { path: 'transactions', component: TransactionListComponent, data: { title: 'Transactions'} },
    { path: 'transaction/:eventId', component: TransactionComponent, data: { title: 'Add New Transactions'} }
  ]),
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TransactionRoutingModule { }
