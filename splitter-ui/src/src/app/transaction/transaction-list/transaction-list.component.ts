import { Component, OnInit } from '@angular/core';
import { EventVO, EventControllerService } from '@app/transaction-management-client';
import { CredentialsService } from '@app/auth';

@Component({
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.scss']
})
export class TransactionListComponent implements OnInit {
  userMobileNo: string;
  events: EventVO[];
  rowIndexesToShow: boolean[];

  constructor(private credentialService: CredentialsService,
    private eventControllerService: EventControllerService) {
    this.userMobileNo = this.credentialService.credentials.username;
   }

  ngOnInit(): void {
    this.eventControllerService.filterEventsUsingGET(
      this.credentialService.credentials.username,
      this.credentialService.credentials.username
    ).subscribe(events => {
      this.events = events;
      /* add default row expansion state */
      this.rowIndexesToShow = [];
      this.events.forEach((e, i) => this.rowIndexesToShow[i] = false);
    });
  }

  onSort(e: any){
    console.log(e);
  }

  expandRow(index: number){
    this.rowIndexesToShow[index] = ! this.rowIndexesToShow[index];
  }

  showRow(index: number){
    return this.rowIndexesToShow[index];
  }

  isCurrentUser(mobileNo: string){
    return this.credentialService.credentials.username === mobileNo;
  }

}
