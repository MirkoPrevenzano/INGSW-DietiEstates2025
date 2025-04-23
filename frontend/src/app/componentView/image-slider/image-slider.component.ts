import { CommonModule } from '@angular/common';
import { AfterViewInit, Component, ElementRef, Input, ViewChild } from '@angular/core';

@Component({
  selector: 'app-image-slider',
  imports: [
    CommonModule
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
  @Input() photos: string[] = []

  

  @ViewChild('slider') slider!: ElementRef

  scrollToSlide(index: number, event?: Event): void {
    if(event) event.preventDefault()
    const sliderElement = this.slider.nativeElement
    const targetImage = sliderElement.querySelector(`#slide-${index}`)

    if (targetImage) {
      targetImage.scrollIntoView({ behavior: 'smooth', block: 'nearest', inline: 'center' })
    }
  }
}
