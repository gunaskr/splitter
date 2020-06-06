import { Component, OnInit, Input } from '@angular/core';
import { TransactionVO } from '@app/transaction-management-client';
import Big from 'big.js';

@Component({
  selector: 'app-transaction-snapshot',
  templateUrl: './transaction-snapshot.component.html',
  styleUrls: ['./transaction-snapshot.component.scss']
})
export class TransactionSnapshotComponent implements OnInit {
  totalAmount: number;
  @Input() transactions: TransactionVO[];
  @Input() transactionType: string;

  constructor() { }

  ngOnInit(): void {
    this.totalAmount = Number(this.calculateTotalAmount(this.transactions).round(2));
  }

  calculateTotalAmount(transactions: TransactionVO[]): Big {
    return transactions.reduce((p, c) => {
      return p.add(new Big(c.amount));
    }, new Big(0));
  }

}
