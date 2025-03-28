import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { forkJoin, map, Observable, switchMap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadPhotoService {

  constructor(private readonly http:HttpClient) { }
   
    url:string="http://localhost:8080/S3"
  
    httpOptions = {
        headers: new HttpHeaders({
          //'Content-Type': 'application/json'
        })
    };

    uploadPhotos(id: number, photos: File[]) {
      const formData = new FormData()
      photos.forEach(photo=>{
        formData.append('photos', photo)
      })
      
      const url = this.url+`/${localStorage.getItem('user')}/upload-photo/${id}`
      return this.http.post(url, formData, this.httpOptions)
    }

    getPhoto(photoKey: string): Observable<Uint8Array> {
      const url = `${this.url}/get-photo`;
      const params = { photoKey: photoKey };
      return this.http.get<Uint8Array>(url, { ...this.httpOptions, params, responseType: 'blob' as 'json' })
    }


    //questo va
    /*getPhotos(realEstateId: number): Observable<any> {
      const url = `${this.url}/get-photos/${realEstateId}`;
      return this.http.get(url, { ...this.httpOptions, responseType: 'arraybuffer' }).pipe(
        map((response: ArrayBuffer) => {
          return new Blob([response]); // Converte l'ArrayBuffer in un Blob
        })
      );       
      
    }*/

    //va, ricevo stringhe
    /*getPhotos(realEstateId: number): Observable<any> {
      const url = `${this.url}/get-photos/${realEstateId}`;
      return this.http.get(url, { ...this.httpOptions, responseType: 'text' });
    }*/
    

    //versione con lista
    getPhotos(realEstateId: number): Observable<string[]> {
      const url = `${this.url}/get-photos/${realEstateId}`;
      return this.http.get<string[]>(url, { ...this.httpOptions, responseType: 'json' });
    }
    
   
    
    
    
     
}
