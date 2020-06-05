import { Component, OnInit, ViewChild } from '@angular/core';
import { CredentialsService } from '@app/auth';
import { EventVO, EventControllerService, TransactionVO } from '@app/transaction-management-client';
import { FormBuilder, FormGroup, Validators, FormControl, FormArray } from '@angular/forms';
import { RoomMateControllerService, User } from '@app/user-management-client';
import { ActivatedRoute } from '@angular/router';
import Big from 'big.js';
import { NgbModal, NgbModalRef, NgbDateAdapter, NgbDate, NgbDateStruct, NgbTimeStruct } from '@ng-bootstrap/ng-bootstrap';
import {NgbTimepickerConfig} from '@ng-bootstrap/ng-bootstrap';
import { NgbTime } from '@ng-bootstrap/ng-bootstrap/timepicker/ngb-time';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.scss'],
  providers: [NgbTimepickerConfig]
})
export class TransactionComponent implements OnInit {
  eventForm: FormGroup;
  users: User[];
  eventId: string;
  isLoading = true;
  @ViewChild('content', { static: false }) private modalContent: any;

  constructor(private credentialService: CredentialsService,
    private eventControlerService: EventControllerService,
    private formBuilder: FormBuilder,
    private roomMateController: RoomMateControllerService,
    private route: ActivatedRoute,
    private modalService: NgbModal,
    config: NgbTimepickerConfig) {
      config.spinners = false;
  }

  ngOnInit(): void {
    this.eventId = this.route.snapshot.params.eventId;
    this.roomMateController.getRoomMatesUsingGET(this.credentialService.credentials.username)
      .subscribe(users => {
        this.users = users;
        if (this.eventId) {
          this.eventControlerService.findEventByIdUsingGET(this.eventId)
            .subscribe(event => {
              this.eventForm = this.createEventForm(event);
              this.isLoading = false;
            })
        } else {
          this.eventForm = this.createEventForm();
          this.isLoading = false;
        }
      })
  }


  validateAndSaveExpense() {
    const eventVO: EventVO = this.eventForm.value;
    this.validateSaveExpense(eventVO);
  }

  resetForm() {

  }

  addTransaction() {
    const transactions = this.eventForm.get('transactions') as FormArray;
    transactions.push(this.createTransactionForm());
  }

  removeTransaction(index: number) {
    const transactions = this.eventForm.get('transactions') as FormArray;
    /*const transaction: FormGroup = transactions.at(index);
    if (transaction.value.id){
    }*/
    transactions.removeAt(index);
  }

  onChangeSplitOption(event: any) {
    if (event.target.checked) {
      const transactionForms = this.eventForm.get('transactions') as FormArray;
      transactionForms.clear();
      const totalAmount: Big = new Big(this.eventForm.value.amountSpent);
      const amountPerUser = totalAmount.div(this.users.length);
      const transactions: TransactionVO[] = this.users.map(user => {
        return {
          amount: amountPerUser.round(2) as any,
          fromUser: {
            mobileNo:user.mobileNo
          },
          toUser: {
            mobileNo:this.credentialService.credentials.username
          }
        }
      });
      transactions.forEach(transaction => {
        transactionForms.push(this.createTransactionForm(transaction));
      });
    }
  }

  private validateSaveExpense(eventVO: EventVO){
    const amountFromTransactions = eventVO.transactions.reduce((pv: Big, cur: TransactionVO) => {
      return pv.add(new Big(cur.amount));
    }, new Big(0));
    const totalAmount = new Big(eventVO.amountSpent);
    if (!totalAmount.minus(amountFromTransactions).lt(new Big(0.1))) {
      const modal: NgbModalRef = this.modalService.open(this.modalContent, { size: 'sm' });
      modal.result.then(result => {
        if(result === 'continue'){
          this.saveExpense(eventVO);
        }
      })
    } else {
      this.saveExpense(eventVO);
    }
  }

  private saveExpense(event: EventVO){
    const eventToSave = JSON.parse(JSON.stringify(event));
    const ngbDate: NgbDate = (event as any).transactionDate;
    const ngbTime: NgbTime = (event as any).transactionTime;
    const backendDate = new Date(ngbDate.year, ngbDate.month - 1, ngbDate.day);
    backendDate.setHours(ngbTime.hour);
    backendDate.setMinutes(ngbTime.minute);
    delete (eventToSave as any).transactionDate;
    delete (eventToSave as any).transactionTime;
    this.isLoading = true;
    eventToSave['createdAt'] = new Date(this.getUTCDateString(ngbDate, ngbTime));
    this.eventControlerService.createEventUsingPOST(eventToSave)
      .subscribe(event => {
        this.eventForm.markAsPristine();
        this.isLoading = false;
      });
  }

  private getUTCDateString(date: NgbDateStruct, time: NgbTimeStruct){
    // '6/29/2011 4:52:48 PM UTC'
    const dateInUTC = date.month + '/' + date.day + '/' + date.year + ' ' 
    + (((time.hour + 0) % 12) || 12) + ':' + time.minute + ':00 ' + ((time.hour + 0) >= 12 ? 'PM' : 'AM') + ' UTC';
    return dateInUTC;
    
  }

  private createEventForm(event?: EventVO) {
    const transactionForms: FormGroup[] = [];
    if (event) {
      event.transactions.forEach(
        transaction => {
          transactionForms.push(this.createTransactionForm(transaction));
        }
      )
    }
    return this.formBuilder.group(
      {
        amountSpent: new FormControl(event ? event.amountSpent : null , [Validators.required, Validators.pattern(/^\d{1,10}(\.\d{1,4})?$/)]),
        category: new FormControl(event ? event.category : '', Validators.required),
        name: new FormControl(event ? event.name : '', Validators.required),
        user: this.formBuilder.group(
          {
            mobileNo: this.credentialService.credentials.username
          }
        ),
        transactionDate: new FormControl(event ? this.getNgbDate(event.createdAt) : '', Validators.required),
        transactionTime: new FormControl(event ? this.getNgbTime(event.createdAt) : '', Validators.required),
        transactions: this.formBuilder.array(transactionForms, Validators.required)
      }
    );
  }

  private getNgbDate(dateInput: Date): NgbDateStruct {
    const date = new Date(new Date(dateInput).toLocaleString());
    return {day: date.getDate(), month: date.getMonth() + 1, year: date.getFullYear()};
  }

  private getNgbTime(dateInput: Date): NgbTimeStruct {
    const date = new Date(new Date(dateInput).toLocaleString());
    return { hour: date.getHours() , minute: date.getMinutes(), second: 0};
  }

  private createTransactionForm(transaction?: TransactionVO) {
    const formGroup = this.formBuilder.group({
      id: transaction ? transaction.id : '',
      fromUser: this.formBuilder.group(
        {
          mobileNo: new FormControl(transaction ? transaction.fromUser.mobileNo : '', Validators.required)
        }
      ),
      toUser: this.formBuilder.group(
        {
          mobileNo: transaction ? transaction.toUser.mobileNo : this.credentialService.credentials.username
        }
      ),
      amount: new FormControl( transaction ? transaction.amount : null , [Validators.required, Validators.pattern(/^\d{1,10}(\.\d{1,4})?$/)])
    });
    return formGroup;
  }

}
