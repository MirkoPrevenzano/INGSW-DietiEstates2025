import { Component, Input } from '@angular/core';
import { RealEstateForRentCreation } from '../../../model/request/realEstateForRentCreation';

@Component({
  selector: 'app-rent-estate-view',
  imports: [],
  templateUrl: './rent-estate-view.component.html',
  styleUrl: './rent-estate-view.component.scss'
})
export class RentEstateViewComponent {
  @Input() estate!: RealEstateForRentCreation;
  
}
