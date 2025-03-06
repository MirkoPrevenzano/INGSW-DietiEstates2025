import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RegisterRequest } from '../../../model/registerRequest';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private readonly http:HttpClient) { }

  url:string="http://localhost:8080/auth"

  httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
  };
    
  registrate(registerRequest:RegisterRequest){
    const url=this.url+"/standard-registration"
    return this.http.post(url, registerRequest, this.httpOptions)
  }
}
