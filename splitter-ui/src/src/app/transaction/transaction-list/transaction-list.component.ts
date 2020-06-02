import { Component, OnInit } from '@angular/core';
import { EventVO } from '@app/transaction-management-client';
import { CredentialsService } from '@app/auth';

@Component({
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.scss']
})
export class TransactionListComponent implements OnInit {
  userMobileNo: string;
  events: EventVO[];

  constructor(private credentialService: CredentialsService) {
    this.userMobileNo = this.credentialService.credentials.username;
   }

  ngOnInit(): void {
  }

  onSort(e: any){

  }

}
