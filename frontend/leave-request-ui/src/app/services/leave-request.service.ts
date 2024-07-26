import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LeaveRequestService {
  api_leave_url: string = 'http://localhost:9090/api/leave-requests';

  constructor(private http: HttpClient, private router: Router) { }

  // save request
  saveRequest(data: any) {
    const authOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + sessionStorage.getItem('__tk'),
      }),
    };
    return this.http.post(this.api_leave_url, data, authOptions).pipe(
      map((val) => {
        return val;
      }),
      catchError((err) => {
        return throwError(err);
      })
    );
  }

  // get requests
  getRequests() {
    const authOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + sessionStorage.getItem('__tk'),
      }),
    };
    return this.http
      .get<any>(this.api_leave_url, authOptions)
      .toPromise()
      .then((response: any) => {
        return response;
      })
      .catch((error: any) => {
        return throwError(error);
      });
  }

  // update request
  updateRequest(data: any) {
    const authOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + sessionStorage.getItem('__tk'),
      }),
    };
    return this.http.put(this.api_leave_url, data, authOptions).pipe(
      map((val) => {
        return val;
      }),
      catchError((err) => {
        return throwError(err);
      })
    );
  }

  // delete request
  deleteRequest(data: any) {
    const authOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + sessionStorage.getItem('__tk'),
      }),
    };
    return this.http.delete(this.api_leave_url+'/'+data, authOptions).pipe(
      map((val) => {
        return val;
      }),
      catchError((err) => {
        return throwError(err);
      })
    );
  }
}
