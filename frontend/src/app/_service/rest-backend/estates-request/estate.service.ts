import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EstatePreview } from '../../../model/estatePreview';
import { Params } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class EstateService {
    
  private url:string="http://localhost:8080/real-estate"
    
  private httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
  };


  constructor(private readonly http: HttpClient) { }

  

  getEstatesNewPage(params:Params): Observable<EstatePreview[]>{
    const url: string = this.url+"/search4";
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

  getEstatesNewFilter(params:Params):Observable<{ realEstatePreviews: EstatePreview[], totalElements: number, totalPages:number }> {
    console.log("Sto in new filter")
    const url: string = this.url+"/search3"
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

  getCountEstates(params: Params):Observable<number>{
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