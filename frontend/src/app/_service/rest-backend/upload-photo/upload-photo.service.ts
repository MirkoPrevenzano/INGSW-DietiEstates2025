import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';

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

    getPhoto(photoKey: string): Observable<Blob> {
      const url = `${this.url}/get-photo`;
      const params = { photoKey: photoKey };
      return this.http.get<Blob>(url, { ...this.httpOptions, params, responseType: 'blob' as 'json' })
    }
  
    
     
}
