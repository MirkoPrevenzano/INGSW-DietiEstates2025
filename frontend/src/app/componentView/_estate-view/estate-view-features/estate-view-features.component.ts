import { Component, Input } from '@angular/core';
import { EstateDescribe } from '../../../model/estateDescribe';
import { EstateFeatures } from '../../../model/estateFeatures';

@Component({
  selector: 'app-estate-view-features',
  imports: [],
  templateUrl: './estate-view-features.component.html',
  styleUrl: './estate-view-features.component.scss'
})
export class EstateViewFeaturesComponent {
  @Input() estateFeatures!: EstateFeatures;
  @Input() estateDescribe!: EstateDescribe;
}
