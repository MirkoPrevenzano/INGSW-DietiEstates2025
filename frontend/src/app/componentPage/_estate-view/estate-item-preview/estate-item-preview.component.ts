import { AfterViewInit, Component, Input } from '@angular/core';
import { ImageSliderComponent } from '../../../componentCustom/image-slider/image-slider.component';
import { RealEstatePreviewInfo } from '../../../model/response/support/realEstatePreviewInfo';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { PhotoResult } from '../../../model/response/photoResult';
import { HandleNotifyService } from '../../../_service/handle-notify.service';
import { RealEstateService } from '../../../rest-backend/real-estate/real-estate.service';

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

   
  @Input() estatePreview!:RealEstatePreviewInfo 
  photos: string[] = []
  @Input() realEstateId!: number

  


  constructor(
    private readonly router: Router,
    private readonly realEstateService: RealEstateService,
    private readonly handleError:HandleNotifyService
  ){}


  ngAfterViewInit(): void {
    this.realEstateService.getPhotos(this.realEstateId).subscribe({
      next: (photos:PhotoResult[]) =>{
        
        this.photos.push(...photos.map(photo => `data:image/${photo.contentType};base64,${photo.photoValue}`));
      },
      error: (err) => {
        this.handleError.showMessageError(err.error)
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
