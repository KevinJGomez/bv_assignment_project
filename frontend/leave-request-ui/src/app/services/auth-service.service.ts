import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {
  api_auth_url: string = 'http://localhost:9090/api/auth/';
  api_admin_url: string = 'http://localhost:9090/api/admin/';

  constructor(private http: HttpClient, private router: Router) {}

  // const authOptions = {
  //   headers: new HttpHeaders({
  //     'Content-Type': 'application/json',
  //     Authorization: 'Bearer ' + sessionStorage.getItem('access_token'),
  //   }),
  // };

  // user login
  login(data: any) {
    // const authOptions = {
    //   headers: new HttpHeaders({
    //     'Content-Type': 'application/json',
    //   }),
    // };
    return this.http.post(this.api_auth_url + 'login', data).pipe(
      map((val) => {
        return val;
      }),
      catchError((err) => {
        return throwError(err);
      })
    );
  }

  // User Registration
  register(data: any) {
    // const authOptions = {
    //   headers: new HttpHeaders({
    //     'Content-Type': 'application/json',
    //   }),
    // };
    return this.http.post(this.api_auth_url + 'register', data).pipe(
      map((val) => {
        return val;
      }),
      catchError((err) => {
        return throwError(err);
      })
    );
  }

  // user verify
  verify(data: any) {
    // const authOptions = {
    //   headers: new HttpHeaders({
    //     'Content-Type': 'application/json',
    //   }),
    // };
    return this.http.post(this.api_auth_url + 'verify', data).pipe(
      map((val) => {
        return val;
      }),
      catchError((err) => {
        return throwError(err);
      })
    );
  }

  // get all users
  getUsers() {
    const authOptions = {
      // headers: new HttpHeaders({
      //   'Content-Type': 'application/json',
      //   Authorization: 'Bearer ' + sessionStorage.getItem('__tk'),
      // }),
    };
    return this.http
      .get<any>(this.api_admin_url + 'users')
      .toPromise()
      .then((response: any) => {
        return response;
      })
      .catch((error: any) => {
        return throwError(error);
      });
  }

  // interceptor configs
  getToken(): string | null {
    return sessionStorage.getItem('__tk');
  }

  setToken(token: string): void {
    sessionStorage.setItem('__tk', token);
  }

  removeToken(): void {
    sessionStorage.removeItem('__tk');
  }
}
