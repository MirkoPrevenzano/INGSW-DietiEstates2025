import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

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

    uploadPhotos(id: number, photos: File){
      const formData = new FormData()
     /* photos.forEach(photo=>{
        formData.append('photos', photo)
      })*/
     formData.append('file', photos)

      for (const pair of (formData as any).entries()) {
        console.log(`${pair[0]}: ${pair[1]}`);
      }
      
      const url = this.url+`/${localStorage.getItem('user')}/upload-photo/${id}`
      return this.http.post(url, formData, this.httpOptions)
    }
}
