import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TransactionRoutingModule } from './transaction-routing.module';
import { TransactionComponent } from './transaction.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TransactionListComponent } from './transaction-list/transaction-list.component';
import { SharedModule } from '@app/@shared';

@NgModule({
  declarations: [TransactionComponent, TransactionListComponent],
  imports: [CommonModule, TransactionRoutingModule, FormsModule, ReactiveFormsModule, NgbModule, SharedModule],
})
export class TransactionModule {}
