import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CustomerRegistration } from '../../model/request/customerRegistration';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private readonly http:HttpClient) { }

  private url:string="https://xqqys2wucm.eu-west-3.awsapprunner.com/auth"

  private httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
  };
    
  registrate(registerRequest:CustomerRegistration){
    const url="https://xqqys2wucm.eu-west-3.awsapprunner.com/customers"
    return this.http.post(url, registerRequest, this.httpOptions)
  }

  loginWithGoogle(credential: any): Observable<any> {
    const url = this.url + "/login/oauth2/code/google";
    const body = { token: credential }; 
    return this.http.post(url, body, this.httpOptions);
  }
}
