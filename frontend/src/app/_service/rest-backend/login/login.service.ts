import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../../../model/loginRequest';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  url = "http://localhost:8080";
  
  constructor(private readonly http: HttpClient) {}

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    })
  };

  login(loginRequest: LoginRequest): Observable<{accessToken: string}> {
    const url = this.url + "/login";
    const body = new HttpParams()
      .set('username', loginRequest.username)
      .set('password', loginRequest.password)
      .set('role', loginRequest.role);

    return this.http.post<{accessToken: string}>(url, body.toString(), this.httpOptions);
  }

  loginWithGoogle(credential: any): Observable<any> {
    const url = this.url + "/auth/login/oauth2/code/google";
    const body = { token: credential }; // Oggetto JSON valido
    return this.http.post(url, body, { headers: { 'Content-Type': 'application/json' } });
  }
}