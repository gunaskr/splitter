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
import { User } from './user';

export interface TransactionVO {
  amount?: number;
  fromUser?: User;
  id?: string;
  toUser?: User;
}
