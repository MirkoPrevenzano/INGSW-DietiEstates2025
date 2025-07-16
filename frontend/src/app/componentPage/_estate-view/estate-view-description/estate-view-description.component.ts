import { AfterViewInit, Component, ElementRef, Input, ViewChild } from '@angular/core';
import { EstateDescribe } from '../../../model/estateDescribe';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-estate-view-description',
  imports: [
    CommonModule
  ],
  templateUrl: './estate-view-description.component.html',
  styleUrl: './estate-view-description.component.scss'
})
export class EstateViewDescriptionComponent implements AfterViewInit{
  @Input() estateDescribe!: EstateDescribe;
  @ViewChild('description') description!: ElementRef;
  @Input() realEstateId!: number



  showFullDescription = false
  showToggleButtons = true

  constructor(
    private readonly route: ActivatedRoute
  ) {}

  

  ngAfterViewInit() {
    this.checkDescriptionHeight();
  }

  toggleDescription() {
    this.showFullDescription = !this.showFullDescription;
  }

  checkDescriptionHeight() {
    const url = this.route.snapshot.url.map(segment => segment.path).join('/');
    const descriptionElement = this.description.nativeElement;
    const lineHeight = parseInt(window.getComputedStyle(descriptionElement).lineHeight, 10);
    const maxHeight = lineHeight * 3; // Altezza massima per 3 righe
    if (
      descriptionElement.scrollHeight > maxHeight && 
      !url.includes('estate') 
    ){
      this.showToggleButtons = true;
    }
  }
}
