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
  userMap: Map<string, User>;

  constructor(
    private credentialService: CredentialsService,
    private transactionControllerService: TransactionControllerService,
    private roomMateControllerService: RoomMateControllerService) { }

  ngOnInit() {
    this.roomMateControllerService.getRoomMatesUsingGET(this.credentialService.credentials.username)
      .subscribe(users => {
        users.unshift({ username: '@Me', mobileNo: this.credentialService.credentials.username });
        this.userMap = new Map();
        users.forEach(user => {
          this.userMap.set(user.mobileNo, user);
        });
        this.transactionControllerService.getTransactionsUsingGET(
          this.credentialService.credentials.username,
          null
        ).subscribe(
          debitTransactions => {
            this.debitTransactions = debitTransactions.filter(transction => {
              return transction.toUserId !== this.credentialService.credentials.username;
            });
            this.totalDebitAmount = Number(this.calculateTotalAmount(this.debitTransactions).round(2));
            this.transactionControllerService.getTransactionsUsingGET(null, this.credentialService.credentials.username)
              .subscribe(creditTransactions => {
                this.creditTransactions = creditTransactions.filter(transction => {
                  return transction.fromUserId !== this.credentialService.credentials.username;
                });
                this.totalCreditAmount = Number(this.calculateTotalAmount(this.creditTransactions).round(2));
              })
          }
        );
      }
      );
  }

  calculateTotalAmount(transactions: TransactionVO[]): Big {
    return transactions.reduce((p, c) => {
      return p.add(new Big(c.amount));
    }, new Big(0));
  }
  
  getUserName(userId:string): string {
    return this.userMap.get(userId) ? this.userMap.get(userId).username: 'Unknown';
  }

  getGenderIcon(userId: string): string {
    return this.userMap.get(userId) ? 
    (this.userMap.get(userId).gender === User.GenderEnum.FEMALE.toString() ? 'fa-female' : 'fa-male'): 'Unknown';
  }
}
