import { Component, OnInit } from '@angular/core';
import { RoomMateControllerService, User, RoomMateDTO } from '@app/user-management-client';
import { CredentialsService } from '@app/auth';
import { FormBuilder, FormGroup, FormArray, FormControl } from '@angular/forms';

@Component({
  selector: 'app-my-roommates',
  templateUrl: './my-roommates.component.html',
  styleUrls: ['./my-roommates.component.scss']
})
export class MyRoommatesComponent implements OnInit {

  users: User[];
  usersForm: FormGroup;
  isLoading = true;

  constructor(private roomMateController: RoomMateControllerService,
    private credentialService: CredentialsService,
    private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.roomMateController.getRoomMatesUsingGET(this.credentialService.credentials.username)
    .subscribe(users => {
      this.users = users;
      const userFormGroups = users.map(user => this.createUserGroup(user));
      this.usersForm = this.formBuilder.group({
        users: this.formBuilder.array(userFormGroups)
      });
      this.isLoading = false;
    })
  }

  createUserGroup(user?: User): FormGroup {
    return this.formBuilder.group({
      name: user ? new FormControl({value: user.username, disabled: true}) : '',
      gender: user ? new FormControl({value: user.gender, disabled: true}) : '',
      mobileNo: user ? new FormControl({value: user.mobileNo, disabled: true}) : '',
      id: user ? user.id : ''
    });
  }

  addUser(){
    const users = this.usersForm.get('users') as FormArray;
    users.push(this.createUserGroup());
  }

  saveDetails() {
    this.isLoading = true;
    const users = this.usersForm.get('users') as FormArray;
    const formGroupsToSave: FormGroup[] = []
    users.controls.forEach(control => {
      if((control as FormGroup).dirty){
        formGroupsToSave.push((control as FormGroup));
      }
    });
    const roomMates: RoomMateDTO[] = [];
    formGroupsToSave.forEach(formGroup => {
      roomMates.push({
        username: formGroup.value.name,
        gender: formGroup.value.gender as RoomMateDTO.GenderEnum,
        mobileNo: formGroup.value.mobileNo,
        addedBy: this.credentialService.credentials.username
      })
    })
    this.roomMateController.createRoomMatesUsingPOST(roomMates)
    .subscribe(response => {
      users.markAsPristine();
      formGroupsToSave.forEach(formGroup => {
        formGroup.disable();
      });
      this.isLoading = false;
    })
  }

  deleteUser(index: number){
    this.isLoading = true;
    const users = this.usersForm.get('users') as FormArray;
    const mobileNo = users.at(index).value.mobileNo;
    const id = users.at(index).value.id;
    if(mobileNo && id){
      this.roomMateController.deleteRoomMateUsingDELETE(this.credentialService.credentials.username,
        mobileNo)
        .subscribe(response => {
          users.removeAt(index);
          this.isLoading = false;
        });
    } else {
      users.removeAt(index);
      this.isLoading = false;
    }
  }

}
