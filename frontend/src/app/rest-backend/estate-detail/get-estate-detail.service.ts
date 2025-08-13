import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Estate } from '../../model/estate';
import { AgentPublicInfo } from '../../model/agentPublicInfo';



@Injectable({
  providedIn: 'root'
})

//deve andare in estate request
export class GetEstateDetailService {

  constructor(private readonly http: HttpClient) { }
  url:string="http://localhost:8080"
      
    httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        })
    };

    getEstateInfo(realEstateId:number):Observable<{realEstateCreationDto:Estate, agentPublicInfo:AgentPublicInfo}>{
      const url = `${this.url}/real-estate/view/${realEstateId}`;
      return this.http.get<{realEstateCreationDto:Estate, agentPublicInfo:AgentPublicInfo}>(url, this.httpOptions);
    }
}
