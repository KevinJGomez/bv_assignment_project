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

  constructor(private http: HttpClient, private router: Router) {}

  // user login
  login(data: any) {
    const authOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
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
    const authOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    return this.http.post(this.api_auth_url + 'register', data).pipe(
      map((val) => {
        return val;
      }),
      catchError((err) => {
        console.log(err);
        return throwError(err);
      })
    );
  }

  // user verify
  verify(data: any) {
    const authOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    return this.http.post(this.api_auth_url + 'verify', data).pipe(
      map((val) => {
        return val;
      }),
      catchError((err) => {
        return throwError(err);
      })
    );
  }
}
