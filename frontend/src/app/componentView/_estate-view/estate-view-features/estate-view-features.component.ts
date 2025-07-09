import { Component, Input } from '@angular/core';
import { EstateDescribe } from '../../../model/estateDescribe';
import { EstateFeatures } from '../../../model/estateFeatures';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-estate-view-features',
  imports: [MatIcon],
  templateUrl: './estate-view-features.component.html',
  styleUrl: './estate-view-features.component.scss'
})
export class EstateViewFeaturesComponent {
  @Input() estateFeatures!: EstateFeatures;
  @Input() estateDescribe!: EstateDescribe;
}
