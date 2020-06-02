import { Component, OnInit } from '@angular/core';
import { CredentialsService } from '@app/auth';
import { EventVO, EventControllerService, TransactionVO } from '@app/transaction-management-client';
import { FormBuilder, FormGroup, Validators, FormControl, FormArray } from '@angular/forms';
import { RoomMateControllerService, User } from '@app/user-management-client';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.scss']
})
export class TransactionComponent implements OnInit {

  eventForm: FormGroup;
  users: User[];

  constructor(private credentialService: CredentialsService,
    private eventControlerService: EventControllerService,
    private formBuilder: FormBuilder,
    private roomMateController: RoomMateControllerService) {
   }

  ngOnInit(): void {
    this.eventForm = this.formBuilder.group(
      {
        amountSpent: new FormControl('', Validators.pattern('^[0-9]$')),
        category: new FormControl(''),
        name: new FormControl(''),
        userId: this.credentialService.credentials.username,
        transactionDate: '',
        transactions: this.formBuilder.array([])
      }
    );
    this.roomMateController.getRoomMatesUsingGET(this.credentialService.credentials.username)
    .subscribe(users => {
      this.users = users;
    })
  }

  saveExpense(){
    const eventVO: EventVO = this.eventForm.value;
    delete (eventVO as any).transactionDate;
    this.eventControlerService.createEventUsingPOST(eventVO)
    .subscribe(event => {
      console.log('event saved ', event);
    })
  }

  resetForm(){

  }

  addTransaction(){
    const transactions = this.eventForm.get('transactions') as FormArray;
    transactions.push(this.createTransactionForm());
  }

  private createTransactionForm(transaction?: TransactionVO){
    const formGroup = this.formBuilder.group({
      fromUserId: transaction ? transaction.fromUserId : '',
      toUserId: transaction ? transaction.toUserId : this.credentialService.credentials.username,
      amount: transaction ? transaction.amount: ''
    });
    return formGroup;
  }

}
