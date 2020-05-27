import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

const credentialsKey = 'credentials';
@Injectable({
  providedIn: 'root',
})
export class TokenInterceptor implements HttpInterceptor {

 constructor() {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const savedCredentials = sessionStorage.getItem(credentialsKey) || localStorage.getItem(credentialsKey);
    if (savedCredentials) {
      const credentials = JSON.parse(savedCredentials);
      request = request.clone({
        setHeaders: {
          'x-auth-token': `${credentials.token}`
        }
      });
    }
    return next.handle(request);
  }
}
