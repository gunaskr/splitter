/**
 * splitter transaction-management REST API
 * This is the description of the splitter transaction-management REST API endpoints.
 *
 * OpenAPI spec version: 0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


export interface User { 
    addedBy?: string;
    gender?: User.GenderEnum;
    mobileNo?: string;
    username?: string;
}
export namespace User {
    export type GenderEnum = 'MALE' | 'FEMALE';
    export const GenderEnum = {
        MALE: 'MALE' as GenderEnum,
        FEMALE: 'FEMALE' as GenderEnum
    };
}
