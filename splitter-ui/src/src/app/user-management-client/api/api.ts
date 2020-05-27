export * from './authenticationController.service';
import { AuthenticationControllerService } from './authenticationController.service';
export * from './roomMateController.service';
import { RoomMateControllerService } from './roomMateController.service';
export * from './signUpController.service';
import { SignUpControllerService } from './signUpController.service';
export const APIS = [AuthenticationControllerService, RoomMateControllerService, SignUpControllerService];
