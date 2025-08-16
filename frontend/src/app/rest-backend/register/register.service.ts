import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CustomerRegistration } from '../../model/request/customerRegistration';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private readonly http:HttpClient) { }

  private url:string="http://localhost:8080/auth"

  private httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
  };
    
  registrate(registerRequest:CustomerRegistration){
    const url=this.url+"/customer-registration"
    return this.http.post(url, registerRequest, this.httpOptions)
  }
}
