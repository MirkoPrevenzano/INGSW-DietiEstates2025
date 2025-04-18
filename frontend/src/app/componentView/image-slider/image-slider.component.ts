import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, ElementRef, Input, ViewChild } from '@angular/core';
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
export class ImageSliderComponent implements AfterViewInit{
  currentIndex=0
  ngAfterViewInit(): void {
    this.autoScrollToSlide()
  }

  autoScrollToSlide() {
    setInterval(()=>{
      this.currentIndex = (this.currentIndex+1)%this.photos.length
      this.scrollToSlide(this.currentIndex)
    }, 3000)
  }

  

 
  @Input() sliderConfig: any
  @Input() photos: string[] = []

  //@ViewChild(SlickCarouselComponent) slickCarousel!: SlickCarouselComponent;

  /*slickPrev() {
    this.slickCarousel.slickPrev();
  }*/

  /*slickNext() {
    this.slickCarousel.slickNext();
  }*/

  @ViewChild('slider') slider!: ElementRef

  scrollToSlide(index: number, event?: Event): void {
    if(event) event.preventDefault()
    const sliderElement = this.slider.nativeElement as HTMLElement
    const targetImage = sliderElement.querySelector(`#slide-${index}`) as HTMLElement

    if (targetImage) {
      targetImage.scrollIntoView({ behavior: 'smooth', block: 'nearest', inline: 'center' })
    }
  }
}
