import { Component} from '@angular/core';
import { MapComponent } from '../map/map.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Coordinate } from '../../model/coordinate';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-map-popup',
  imports: [
    MapComponent,
    FormsModule,
    CommonModule
  ],
  templateUrl: './map-popup.component.html',
  styleUrl: './map-popup.component.scss'
})
export class MapPopupComponent {
   
  radius: number = 100
  coordinate!: Coordinate
  

  constructor(public dialogRef: MatDialogRef<MapPopupComponent>) {}

  submit(){
    this.dialogRef.close({
      radius: this.radius,
      coordinate: this.coordinate
    })
  }

  onClickMarker(coordinate:Coordinate){
    this.coordinate = coordinate;
  }
}
