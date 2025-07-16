import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Estate } from '../../model/estate';

@Injectable({
  providedIn: 'root'
})

//deve andare in estate request
export class GetEstateDetailService {

  constructor(private readonly http: HttpClient) { }
  url:string="http://localhost:8080/customer"
      
    httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        })
    };

    getEstateInfo(realEstateId:number):Observable<Estate>{
      const url = `${this.url}/${localStorage.getItem('user')}/view/${realEstateId}`;
      return this.http.get<Estate>(url, this.httpOptions);
    }
}
