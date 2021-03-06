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
/* tslint:disable:no-unused-variable member-ordering */

import { Inject, Injectable, Optional } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse, HttpEvent } from '@angular/common/http';
import { CustomHttpUrlEncodingCodec } from '../encoder';

import { Observable } from 'rxjs';

import { User } from '../model/user';
import { UserDTO } from '../model/userDTO';

import { BASE_PATH, COLLECTION_FORMATS } from '../variables';
import { Configuration } from '../configuration';

@Injectable()
export class RoomMateControllerService {
  protected basePath = 'https://localhost:8090/user-management';
  public defaultHeaders = new HttpHeaders();
  public configuration = new Configuration();

  constructor(
    protected httpClient: HttpClient,
    @Optional() @Inject(BASE_PATH) basePath: string,
    @Optional() configuration: Configuration
  ) {
    if (basePath) {
      this.basePath = basePath;
    }
    if (configuration) {
      this.configuration = configuration;
      this.basePath = basePath || configuration.basePath || this.basePath;
    }
  }

  /**
   * @param consumes string[] mime-types
   * @return true: consumes contains 'multipart/form-data', false: otherwise
   */
  private canConsumeForm(consumes: string[]): boolean {
    const form = 'multipart/form-data';
    for (const consume of consumes) {
      if (form === consume) {
        return true;
      }
    }
    return false;
  }

  /**
   * createRoomMates
   *
   * @param roomMates roomMates
   * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
   * @param reportProgress flag to report request and response progress.
   */
  public createRoomMatesUsingPOST(
    roomMates: Array<UserDTO>,
    observe?: 'body',
    reportProgress?: boolean
  ): Observable<Array<User>>;
  public createRoomMatesUsingPOST(
    roomMates: Array<UserDTO>,
    observe?: 'response',
    reportProgress?: boolean
  ): Observable<HttpResponse<Array<User>>>;
  public createRoomMatesUsingPOST(
    roomMates: Array<UserDTO>,
    observe?: 'events',
    reportProgress?: boolean
  ): Observable<HttpEvent<Array<User>>>;
  public createRoomMatesUsingPOST(
    roomMates: Array<UserDTO>,
    observe: any = 'body',
    reportProgress: boolean = false
  ): Observable<any> {
    if (roomMates === null || roomMates === undefined) {
      throw new Error('Required parameter roomMates was null or undefined when calling createRoomMatesUsingPOST.');
    }

    let headers = this.defaultHeaders;

    // authentication (x-auth-token) required
    if (this.configuration.apiKeys && this.configuration.apiKeys['x-auth-token']) {
      headers = headers.set('x-auth-token', this.configuration.apiKeys['x-auth-token']);
    }

    // to determine the Accept header
    let httpHeaderAccepts: string[] = ['*/*'];
    const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
    if (httpHeaderAcceptSelected != undefined) {
      headers = headers.set('Accept', httpHeaderAcceptSelected);
    }

    // to determine the Content-Type header
    const consumes: string[] = ['application/json'];
    const httpContentTypeSelected: string | undefined = this.configuration.selectHeaderContentType(consumes);
    if (httpContentTypeSelected != undefined) {
      headers = headers.set('Content-Type', httpContentTypeSelected);
    }

    return this.httpClient.post<Array<User>>(`${this.basePath}/api/v1/roommate`, roomMates, {
      withCredentials: this.configuration.withCredentials,
      headers: headers,
      observe: observe,
      reportProgress: reportProgress,
    });
  }

  /**
   * deleteRoomMate
   *
   * @param addedBy addedBy
   * @param mobileNo mobileNo
   * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
   * @param reportProgress flag to report request and response progress.
   */
  public deleteRoomMateUsingDELETE(
    addedBy: string,
    mobileNo: string,
    observe?: 'body',
    reportProgress?: boolean
  ): Observable<any>;
  public deleteRoomMateUsingDELETE(
    addedBy: string,
    mobileNo: string,
    observe?: 'response',
    reportProgress?: boolean
  ): Observable<HttpResponse<any>>;
  public deleteRoomMateUsingDELETE(
    addedBy: string,
    mobileNo: string,
    observe?: 'events',
    reportProgress?: boolean
  ): Observable<HttpEvent<any>>;
  public deleteRoomMateUsingDELETE(
    addedBy: string,
    mobileNo: string,
    observe: any = 'body',
    reportProgress: boolean = false
  ): Observable<any> {
    if (addedBy === null || addedBy === undefined) {
      throw new Error('Required parameter addedBy was null or undefined when calling deleteRoomMateUsingDELETE.');
    }

    if (mobileNo === null || mobileNo === undefined) {
      throw new Error('Required parameter mobileNo was null or undefined when calling deleteRoomMateUsingDELETE.');
    }

    let headers = this.defaultHeaders;

    // authentication (x-auth-token) required
    if (this.configuration.apiKeys && this.configuration.apiKeys['x-auth-token']) {
      headers = headers.set('x-auth-token', this.configuration.apiKeys['x-auth-token']);
    }

    // to determine the Accept header
    let httpHeaderAccepts: string[] = ['*/*'];
    const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
    if (httpHeaderAcceptSelected != undefined) {
      headers = headers.set('Accept', httpHeaderAcceptSelected);
    }

    // to determine the Content-Type header
    const consumes: string[] = [];

    return this.httpClient.delete<any>(
      `${this.basePath}/api/v1/roommate/${encodeURIComponent(String(mobileNo))}/${encodeURIComponent(String(addedBy))}`,
      {
        withCredentials: this.configuration.withCredentials,
        headers: headers,
        observe: observe,
        reportProgress: reportProgress,
      }
    );
  }

  /**
   * getRoomMates
   *
   * @param mobileNo mobileNo
   * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
   * @param reportProgress flag to report request and response progress.
   */
  public getRoomMatesUsingGET(mobileNo: string, observe?: 'body', reportProgress?: boolean): Observable<Array<User>>;
  public getRoomMatesUsingGET(
    mobileNo: string,
    observe?: 'response',
    reportProgress?: boolean
  ): Observable<HttpResponse<Array<User>>>;
  public getRoomMatesUsingGET(
    mobileNo: string,
    observe?: 'events',
    reportProgress?: boolean
  ): Observable<HttpEvent<Array<User>>>;
  public getRoomMatesUsingGET(
    mobileNo: string,
    observe: any = 'body',
    reportProgress: boolean = false
  ): Observable<any> {
    if (mobileNo === null || mobileNo === undefined) {
      throw new Error('Required parameter mobileNo was null or undefined when calling getRoomMatesUsingGET.');
    }

    let headers = this.defaultHeaders;

    // authentication (x-auth-token) required
    if (this.configuration.apiKeys && this.configuration.apiKeys['x-auth-token']) {
      headers = headers.set('x-auth-token', this.configuration.apiKeys['x-auth-token']);
    }

    // to determine the Accept header
    let httpHeaderAccepts: string[] = ['*/*'];
    const httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
    if (httpHeaderAcceptSelected != undefined) {
      headers = headers.set('Accept', httpHeaderAcceptSelected);
    }

    // to determine the Content-Type header
    const consumes: string[] = [];

    return this.httpClient.get<Array<User>>(
      `${this.basePath}/api/v1/roommate/${encodeURIComponent(String(mobileNo))}`,
      {
        withCredentials: this.configuration.withCredentials,
        headers: headers,
        observe: observe,
        reportProgress: reportProgress,
      }
    );
  }
}
