import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RealEstateCreation } from '../../model/request/realEstateCreation';
import { PhotoResult } from '../../model/response/photoResult';
import { RealEstateCompleteInfo } from '../../model/response/realEstateCompleteInfo';
import { Params } from '@angular/router';
import { RealEstateSearch } from '../../model/response/realEstateSearch';

@Injectable({
  providedIn: 'root'
})
export class RealEstateService {

  private readonly url = 'http://localhost:8080/real-estates';

  constructor(private readonly http: HttpClient) {}

  private readonly httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  createEstate(estate: RealEstateCreation): Observable<any> {
    return this.http.post(this.url, estate, this.httpOptions);
  }


  uploadPhotos(id: number, photos: File[]) {
    const formData = new FormData()
    photos.forEach(photo=>{
      formData.append('photos', photo)
    })
    
    const url = this.url+`${id}/photos`
    return this.http.post(url, formData, {headers: new HttpHeaders({})})
  }

   
  getPhotos(realEstateId: number): Observable<PhotoResult[]> {
    const url = `${this.url}/${realEstateId}/photos`;
    return this.http.get<PhotoResult[]>(url, { responseType: 'json' });
  }

  getEstateInfo(realEstateId:number):Observable<RealEstateCompleteInfo>{
      const url = `${this.url}/${realEstateId}`;
      return this.http.get<RealEstateCompleteInfo>(url, this.httpOptions);
  }

  getEstatesNewFilter(params:Params):Observable<RealEstateSearch> {
    const url: string = this.url
    const httpParams = new HttpParams({ fromObject: params });
    return this.http.get<RealEstateSearch>(
      url, 
      { params: httpParams, 
        headers: this.httpOptions.headers 
      }
    );
  }


}
