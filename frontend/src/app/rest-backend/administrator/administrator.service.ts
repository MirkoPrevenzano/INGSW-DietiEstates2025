import { Injectable } from '@angular/core';
import { CollaboratorCreation } from '../../model/request/collaboratorCreation';
import { environment } from '../../../environments/environment';
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

  private url=`${environment.apiBaseUrl}/admins`


  saveAdmin( newAdmin: CollaboratorCreation)
  {
    const url= this.url+`/collaborators`
    return this.http.post(url,newAdmin,this.httpOptions)
  }
}
