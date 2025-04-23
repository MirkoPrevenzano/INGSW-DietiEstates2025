import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

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

    //versione con lista
    getPhotos(realEstateId: number): Observable<string[]> {
      const url = `${this.url}/get-photos/${realEstateId}`;
      return this.http.get<string[]>(url, { responseType: 'json' });
    }
   
    
    
    
     
}
