import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AgencyRegistration } from '../../model/request/agencyRegistration';

@Injectable({
  providedIn: 'root'
})
export class AgencyService {

  constructor(private readonly http:HttpClient){}

  private httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

  private url="http://localhost:8080/agencies"

  registerAgency(agency:AgencyRegistration){
    return this.http.post(this.url, agency, this.httpOptions)
  }
  


}
