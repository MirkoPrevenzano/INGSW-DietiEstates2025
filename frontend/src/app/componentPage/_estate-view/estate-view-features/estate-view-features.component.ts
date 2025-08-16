import { Component, Input } from '@angular/core';
import { RealEstateMainFeatures } from '../../../model/request/support/realEstateMainFeatures';
import { RealEstateBooleanFeatures } from '../../../model/request/support/realEstateBooleanFeatures';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-estate-view-features',
  imports: [MatIcon],
  templateUrl: './estate-view-features.component.html',
  styleUrl: './estate-view-features.component.scss'
})
export class EstateViewFeaturesComponent {
  @Input() estateFeatures!: RealEstateBooleanFeatures;
  @Input() estateDescribe!: RealEstateMainFeatures;
}
