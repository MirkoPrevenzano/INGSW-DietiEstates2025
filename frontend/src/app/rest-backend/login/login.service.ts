import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../../model/request/loginRequest';
import { Observable } from 'rxjs';
import { Authentication } from '../../model/response/authentication';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private url = "http://localhost:8080";
  
  constructor(private readonly http: HttpClient) {}

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    })
  };

  login(loginRequest: LoginRequest): Observable<Authentication> {
    const url = this.url + "/login";
    const body = new HttpParams()
      .set('username', loginRequest.username)
      .set('password', loginRequest.password)
      .set('role', loginRequest.role);

    return this.http.post<Authentication>(url, body.toString(), this.httpOptions);
  }

  

 
}