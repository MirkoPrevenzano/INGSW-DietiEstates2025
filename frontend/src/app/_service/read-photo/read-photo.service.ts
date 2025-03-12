import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ReadPhotoService {

  constructor() { }

  blobToPhoto(photos:Blob[]): string[]{
    let images: string[] = []
    const reader =this.initReader(images)
    for(const photo of photos){
      const selectedFile = new File([photo], "filename", { type: photo.type });
      reader.readAsDataURL(selectedFile) 
    }

    return images
    
  }
  initReader(images:string[]): FileReader{
    const reader = new FileReader()
    reader.onload = ()=>{
      images.push(reader.result as string)
    }
    return reader
  }
}
