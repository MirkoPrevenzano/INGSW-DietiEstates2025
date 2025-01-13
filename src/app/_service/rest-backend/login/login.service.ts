import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../model/loginRequest';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  

  url = "http://localhost:8080/auth" 
  constructor(private readonly http: HttpClient) {}
 

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  login(loginRequest: LoginRequest):Observable<{ token: string; username: string }>
  {
    const url=this.url+"/authenticate"
    return this.http.post<{ token: string; username: string }>(url,loginRequest, this.httpOptions)
  }

  loginWithGoogle(credential: any) {
    const url= this.url+"/login/oauth2/code/google"
    return this.http.post(url, {token:credential}, this.httpOptions )
  }


}
