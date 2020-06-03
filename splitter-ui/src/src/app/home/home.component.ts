import { Component, OnInit } from '@angular/core';
import { finalize } from 'rxjs/operators';
import { RoomMateControllerService, User, RoomMateDTO } from '@app/user-management-client';
import { CredentialsService } from '@app/auth';
import { TransactionControllerService, TransactionVO } from '@app/transaction-management-client';
import Big from 'big.js';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  users: User[];
  debitTransactions: TransactionVO[];
  creditTransactions: TransactionVO[];
  totalDebitAmount: number;
  totalCreditAmount: number;

  constructor(
    private credentialService: CredentialsService,
    private transactionControllerService: TransactionControllerService) {}

  ngOnInit() {
    this.transactionControllerService.getTransactionsUsingGET(
      this.credentialService.credentials.username,
      null
    ).subscribe(
      debitTransactions =>
      {
        this.debitTransactions = debitTransactions;
        this.totalDebitAmount = Number(this.calculateTotalAmount(debitTransactions).round(2));
        this.transactionControllerService.getTransactionsUsingGET(null, this.credentialService.credentials.username)
        .subscribe(creditTransactions => {
          this.creditTransactions = creditTransactions;
          this.totalCreditAmount = Number(this.calculateTotalAmount(creditTransactions).round(2));
        })
      }
    );
    
  }
  calculateTotalAmount(transactions: TransactionVO[]): Big {
    return transactions.reduce((p, c) => {
      return p.add(new Big(c.amount));
    }, new Big(0));
  }
}
