import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ReadPhotoService {

  constructor() { }

  async blobsToPhotos(blobs: Blob): Promise<string> {
    const images: string[] = [];
    /*for (const blob of blobs) {
      const base64 = await this.convertBlobToBase64(blob);
      images.push(base64);
    }*/
    return this.convertBlobToBase64(blobs);
  }

  private convertBlobToBase64(blob: Blob): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onloadend = () => {
        const base64Data = reader.result as string;
        resolve(base64Data);
      };
      reader.onerror = (error) => {
        reject(error);
      };
      reader.readAsDataURL(blob);
    });
  }
}
