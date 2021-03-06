/**
 * splitter user-management REST API
 * This is the description of the splitter user-management REST API endpoints.
 *
 * OpenAPI spec version: 0.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
import { GrantedAuthority } from './grantedAuthority';

export interface User {
  accountNonExpired?: boolean;
  accountNonLocked?: boolean;
  addedBy?: string;
  authorities?: Array<GrantedAuthority>;
  createdAt?: string;
  credentialsNonExpired?: boolean;
  enabled?: boolean;
  gender?: User.GenderEnum;
  mobileNo?: string;
  updatedAt?: string;
  username?: string;
}
export namespace User {
  export type GenderEnum = 'MALE' | 'FEMALE';
  export const GenderEnum = {
    MALE: 'MALE' as GenderEnum,
    FEMALE: 'FEMALE' as GenderEnum,
  };
}
