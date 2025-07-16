import { AfterViewInit, Component, Input } from '@angular/core';
import { ImageSliderComponent } from '../../../componentCustom/image-slider/image-slider.component';
import { EstatePreview } from '../../../model/estatePreview';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { UploadPhotoService } from '../../../rest-backend/upload-photo/upload-photo.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-estate-item-preview',
  imports: [
    ImageSliderComponent,
    CommonModule
  ],
  templateUrl: './estate-item-preview.component.html',
  styleUrl: './estate-item-preview.component.scss'
})
export class EstateItemPreviewComponent implements AfterViewInit{

   
  @Input() estatePreview!:EstatePreview 
  photos: string[] = []
  @Input() realEstateId!: number

  constructor(
    private readonly router: Router,
    private readonly uploadPhotoService: UploadPhotoService,
    private readonly notifyService: ToastrService
  ){}


  ngAfterViewInit(): void {
    this.uploadPhotoService.getPhotos(this.realEstateId).subscribe({
      next: (photos:string[]) =>{
        this.photos.push(...photos.map(photo => `data:image/jpeg;base64,${photo}`));
      },
      error: (err) => {
        if(err?.error.status >= 400 && err?.error.status < 500)
          this.notifyService.warning(err?.error.description)
        if(err?.error.status >= 500 && err?.error.status < 600)
          this.notifyService.error(err?.error.description)
      }
    })
  }

  showEstateDetail(){
    this.router.navigate([`/estate/${this.realEstateId}`])
  }

  ngOnChanges(){
    console.log(this.photos)
  }

  
}
