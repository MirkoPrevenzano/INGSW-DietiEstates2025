import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RealEstateCreation } from '../../model/request/realEstateCreation';
import { AgentPublicInfo } from '../../model/response/support/agentPublicInfo';
import { RealEstateCompleteInfo } from '../../model/response/realEstateCompleteInfo';



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

    getEstateInfo(realEstateId:number):Observable<RealEstateCompleteInfo>{
      const url = `${this.url}/real-estate/view/${realEstateId}`;
      return this.http.get<RealEstateCompleteInfo>(url, this.httpOptions);
    }
}
