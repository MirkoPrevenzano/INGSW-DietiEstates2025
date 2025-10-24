import { Injectable } from '@angular/core';
import { CollaboratorCreation } from '../../model/request/collaboratorCreation';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdministratorService {
  constructor(private readonly http: HttpClient) { }

  private httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

  private url="https://xqqys2wucm.eu-west-3.awsapprunner.com/admins"


  saveAdmin( newAdmin: CollaboratorCreation)
  {
    const url= this.url+`/collaborators`
    return this.http.post(url,newAdmin,this.httpOptions)
  }
}
