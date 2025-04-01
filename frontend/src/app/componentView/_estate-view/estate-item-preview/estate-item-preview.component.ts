import { Component, Input } from '@angular/core';
import { ImageSliderComponent } from '../../image-slider/image-slider.component';
import { EstatePreview } from '../../../model/estatePreview';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-estate-item-preview',
  imports: [
    ImageSliderComponent,
    CommonModule
  ],
  templateUrl: './estate-item-preview.component.html',
  styleUrl: './estate-item-preview.component.scss'
})
export class EstateItemPreviewComponent {

   
  @Input() estatePreview!:EstatePreview 
  @Input() photos: string[] = []
  @Input() realEstateId!: number

  constructor(private readonly router: Router){}

  showEstateDetail(){
    this.router.navigate([`/estate/${this.realEstateId}`])
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
