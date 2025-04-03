import { AfterViewInit, Component, Input } from '@angular/core';
import { ImageSliderComponent } from '../../image-slider/image-slider.component';
import { EstatePreview } from '../../../model/estatePreview';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { UploadPhotoService } from '../../../_service/rest-backend/upload-photo/upload-photo.service';

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
    private readonly uploadPhotoService: UploadPhotoService
  ){}


  ngAfterViewInit(): void {
    this.uploadPhotoService.getPhotos(this.realEstateId).subscribe(
      (photos:string[])=>{
        this.photos.push(...photos.map(photo => `data:image/jpeg;base64,${photo}`));
      }
    )
  }

  showEstateDetail(){
    this.router.navigate([`/estate/${this.realEstateId}`])
  }

  ngOnChanges(){
    console.log(this.photos)
  }

  sliderConfig = {
    slidesToShow: 1,
    slidesToScroll: 1,
    dots: true,
    infinite: true,
    autoplay: true,
    autoplaySpeed: 2000
  };
}
