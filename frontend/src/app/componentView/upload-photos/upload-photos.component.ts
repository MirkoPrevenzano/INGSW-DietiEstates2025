import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-upload-photos',
  imports: [CommonModule],
  templateUrl: './upload-photos.component.html',
  styleUrl: './upload-photos.component.scss'
})
export class UploadPhotosComponent {
  selectedFiles: File[] = []
  imagePreview: string [] = []
  @Output() imagesOut = new EventEmitter<string[]>()
  @Output() fileOut = new EventEmitter<File[]> ()


  maxImage: number= 10

  onFileSelected(event:Event){
    const input = event.target as HTMLInputElement

    if(input.files){
      for (const file of Array.from(input.files)) {
        this.selectedFiles.push(file);
        const reader = new FileReader();
        reader.onload = () => {
          this.imagePreview.push(reader.result as string);
          this.imagesOut.emit(this.imagePreview)
          this.fileOut.emit(this.selectedFiles)

        };
        reader.readAsDataURL(file);
      }
    }
  }

  removeImage(image:string){
    const index=this.imagePreview.indexOf(image)

    if(index>=0){
      this.imagePreview.splice(index,1)
      this.selectedFiles.splice(index,1)
      this.imagesOut.emit(this.imagePreview)
      this.fileOut.emit(this.selectedFiles)
    }

  }
}
