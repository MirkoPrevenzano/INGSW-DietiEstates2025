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

  private url="https://xqqys2wucm.eu-west-3.awsapprunner.com/agencies"

  registerAgency(agency:AgencyRegistration){
    return this.http.post(this.url, agency, this.httpOptions)
  }
  


}
