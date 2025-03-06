import { CommonModule } from '@angular/common';
import { Component, Input, ViewChild } from '@angular/core';
import { SlickCarouselComponent, SlickCarouselModule } from 'ngx-slick-carousel';

@Component({
  selector: 'app-image-slider',
  imports: [
    CommonModule,
    SlickCarouselModule
    
  ],
  templateUrl: './image-slider.component.html',
  styleUrl: './image-slider.component.scss'
})
export class ImageSliderComponent {
  @Input() sliderConfig: any
  @Input() photos: string[] = []

  @ViewChild(SlickCarouselComponent) slickCarousel!: SlickCarouselComponent;

  slickPrev() {
    this.slickCarousel.slickPrev();
  }

  slickNext() {
    this.slickCarousel.slickNext();
  }
}
