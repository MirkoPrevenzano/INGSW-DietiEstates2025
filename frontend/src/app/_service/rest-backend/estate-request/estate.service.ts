import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EstatePreview } from '../../../model/estatePreview';

@Injectable({
  providedIn: 'root'
})
export class EstateService {
    
  url:string="http://localhost:8080/real-estate"
    
  httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
  };


  constructor(private readonly http: HttpClient) { }

  

  getEstatesNewPage(params: { [key: string]: any; }): Observable<EstatePreview[]>{
    const url: string = this.url+"/search2";
    console.log("Sto in new page")
    const httpParams = new HttpParams({ fromObject: params });
    return this.http.get< 
      EstatePreview[]
    >(
      url, 
      { params: httpParams, 
        headers: this.httpOptions.headers 
      }
    );
  }

  getEstatesNewFilter(params: { [key: string]: any; }):Observable<{ realEstatePreviews: EstatePreview[], totalElements: number, totalPages:number }> {
    console.log("Sto in new filter")
    const url: string = this.url+"/search"
    const httpParams = new HttpParams({ fromObject: params });
    return this.http.get<{ 
      realEstatePreviews: EstatePreview[], 
      totalElements: number,
      totalPages: number 
    }>(
      url, 
      { params: httpParams, 
        headers: this.httpOptions.headers 
      }
    );
  }

  

  
  getPhotos(ids: number[]): Observable<string[][]> {
    const url= this.url+"/photos"
    const httpParams = new HttpParams().set('ids', ids.join(','));
    return this.http.get<string[][]>(url, { params: httpParams });
  }

  getCountEstates(params: { [key: string]: any }):Observable<number>{
    const url = this.url+"/count"
    return this.http.get<number>(
      url, 
      { 
        params: new HttpParams(
          { 
            fromObject: params 
          }
        ), 
        headers: this.httpOptions.headers 
      }
    )
  }
}