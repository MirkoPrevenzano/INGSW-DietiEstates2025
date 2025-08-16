import { Component, Input } from '@angular/core';
import { RealEstateForSellCreation } from '../../../model/request/realEstateForSellCreation';

@Component({
  selector: 'app-sell-estate-view',
  imports: [],
  templateUrl: './sell-estate-view.component.html',
  styleUrl: './sell-estate-view.component.scss'
})
export class SellEstateViewComponent {
  @Input() estate!:RealEstateForSellCreation
}
