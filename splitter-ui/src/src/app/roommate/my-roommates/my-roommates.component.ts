import { Component, OnInit } from '@angular/core';
import { RoomMateControllerService, User, RoomMateDTO } from '@app/user-management-client';
import { CredentialsService } from '@app/auth';
import { FormBuilder, FormGroup, FormArray } from '@angular/forms';

@Component({
  selector: 'app-my-roommates',
  templateUrl: './my-roommates.component.html',
  styleUrls: ['./my-roommates.component.scss']
})
export class MyRoommatesComponent implements OnInit {

  users: User[];
  usersForm: FormGroup;

  constructor(private roomMateController: RoomMateControllerService,
    private credentialService: CredentialsService,
    private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.usersForm = this.formBuilder.group({
      users: this.formBuilder.array([ this.createUserGroup() ])
    })
    this.roomMateController.getRoomMatesUsingGET(this.credentialService.credentials.username)
    .subscribe(users => {
      this.users = users;
    })
  }

  createUserGroup(isEditable = true): FormGroup {
    return this.formBuilder.group({
      name: '',
      gender: '',
      mobileNo: '',
      editable: isEditable
    });
  }

  addUser(){
    const users = this.usersForm.get('users') as FormArray;
    users.push(this.createUserGroup());
  }

  saveDetails(){
    const users = this.usersForm.get('users') as FormArray;
    console.log(users.value);
    const roomMates: RoomMateDTO[] = [];
    this.roomMateController.createRoomMatesUsingPOST(roomMates);
  }

}
