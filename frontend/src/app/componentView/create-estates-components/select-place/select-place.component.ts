import { Component, EventEmitter, inject, OnInit, Output, AfterViewInit, ViewChild } from '@angular/core';
import { AddressEstatesComponent } from '../../address-estates/address-estates.component';
import { MapComponent } from '../../map/map.component';
import { Coordinate } from '../../../model/coordinate';
import { Address } from '../../../model/address';
import { EstateDataService } from '../../../_service/estate-data/estate-data.service';

@Component({
  selector: 'app-select-place',
  imports: [AddressEstatesComponent, MapComponent],
  templateUrl: './select-place.component.html',
  styleUrl: './select-place.component.scss'
})
export class SelectPlaceComponent implements OnInit, AfterViewInit{
  @ViewChild(AddressEstatesComponent) addressComponent!: AddressEstatesComponent;
  @ViewChild(MapComponent) mapComponent!: MapComponent;
  
  private readonly estateDataService: EstateDataService = inject(EstateDataService)
  private savedAddress: Address | null = null;
  
  ngOnInit(): void {
    // Store the saved address but don't try to use it yet
    this.savedAddress = this.estateDataService.getAddress();
  }

  ngAfterViewInit(): void {
    // Now that child components are ready, populate with saved data
    if (this.savedAddress?.latitude && this.savedAddress?.longitude) {
      const coordinates = { lat: this.savedAddress.latitude, lon: this.savedAddress.longitude };
      this.onCoordinateChanged(coordinates);
      this.onClickMarker(coordinates);
    }
  }

  currentCoordinates?:Coordinate
  coordinatesByMarker?:Coordinate
  @Output() addressOut = new EventEmitter<Address>();
  
  onCoordinateChanged(event: Coordinate):void{
    this.currentCoordinates = event;
  }

  onClickMarker(event: Coordinate){
    this.coordinatesByMarker = event
  }

  onAddressOut(event: Address){
    this.addressOut.emit(event)
  }


}
