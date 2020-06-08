import { Component, OnInit } from '@angular/core';
import { finalize } from 'rxjs/operators';
import { RoomMateControllerService, User } from '@app/user-management-client';
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
  
  totalCreditAmount: number;
  isLoading = true;

  constructor(
    private credentialService: CredentialsService,
    private transactionControllerService: TransactionControllerService,
    private roomMateControllerService: RoomMateControllerService) { }

  ngOnInit() {
    this.transactionControllerService.getTransactionsUsingGET(
      this.credentialService.credentials.username,
      null
    ).subscribe(
      debitTransactions => {
        this.debitTransactions = debitTransactions.filter(transction => {
          return transction.toUser.mobileNo !== this.credentialService.credentials.username;
        });
        this.transactionControllerService.getTransactionsUsingGET(null, this.credentialService.credentials.username)
          .subscribe(creditTransactions => {
            this.creditTransactions = creditTransactions.filter(transction => {
              return transction.fromUser.mobileNo !== this.credentialService.credentials.username;
            });
            this.totalCreditAmount = Number(this.calculateTotalAmount(this.creditTransactions).round(2));
            this.isLoading = false;
          })
      }
    );
  }

  calculateTotalAmount(transactions: TransactionVO[]): Big {
    return transactions.reduce((p, c) => {
      return p.add(new Big(c.amount));
    }, new Big(0));
  }

  getGenderIcon(user: User): string {
    return user ? 
    (user.gender === User.GenderEnum.FEMALE.toString() ? 'fa-female' : 'fa-male'): 'Unknown';
  }
}
