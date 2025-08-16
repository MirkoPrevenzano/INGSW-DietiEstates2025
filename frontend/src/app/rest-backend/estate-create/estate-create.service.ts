import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RealEstateCreation } from '../../model/request/realEstateCreation';

@Injectable({
  providedIn: 'root'
})
export class EstateCreateService {
  private readonly url = 'http://localhost:8080/agent';

  constructor(private readonly http: HttpClient) {}

  private readonly httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  createEstate(estate: RealEstateCreation): Observable<any> {
    const userId = localStorage.getItem('user');
    let endpoint = '';

    switch (estate.contractType) {
      case 'For Sale':
        endpoint = 'create-real-estate-for-sale';
        break;
      case 'For Rent':
        endpoint = 'create-real-estate-for-rent';
        break;
      default:
        throw new Error('Unknown estate type');
    }

    const url = `${this.url}/${userId}/${endpoint}`;
    return this.http.post(url, estate, this.httpOptions);
  }

}
