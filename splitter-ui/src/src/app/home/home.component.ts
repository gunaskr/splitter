import { Component, OnInit } from '@angular/core';
import { finalize } from 'rxjs/operators';
import { RoomMateControllerService, User, RoomMateDTO } from '@app/user-management-client';
import { CredentialsService } from '@app/auth';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  quote: string | undefined;
  isLoading = false;
  users: User[];

  constructor(private roomMateControllerService: RoomMateControllerService,
    private credentialService: CredentialsService) {}

  ngOnInit() {
    this.isLoading = true;
    const roomMateDto: RoomMateDTO = {
      username: 'test 1',
      mobileNo: '123456789',
      addedBy: this.credentialService.credentials.username,
      gender: RoomMateDTO.GenderEnum.FEMALE
    }
    this.roomMateControllerService
    .createRoomMatesUsingPOST([roomMateDto])
    .subscribe(response => {
      this.roomMateControllerService
      .getRoomMatesUsingGET(this.credentialService.credentials.username)
      .pipe(
        finalize(() => {
          this.isLoading = false;
        })
      )
      .subscribe((users: User[]) => {
        this.users = users;
      });
    })
    
  }
}
