import { Component, Input } from '@angular/core';
import { Address } from '../../../model/address';
import { EstateLocationFeatures } from '../../../model/estateLocationFeatures';
import { MapComponent } from '../../../componentCustom/map/map.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-estate-view-map',
  imports: [
    MapComponent,
    CommonModule
  ],
  templateUrl: './estate-view-map.component.html',
  styleUrl: './estate-view-map.component.scss'
})
export class EstateViewMapComponent{
  @Input() address!: Address;
  @Input() estateLocationFeatures?: EstateLocationFeatures;
}
