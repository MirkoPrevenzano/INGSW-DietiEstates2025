import { Component, EventEmitter, inject, OnInit, Output } from '@angular/core';
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
export class SelectPlaceComponent implements OnInit{
  private readonly estateDataService: EstateDataService = inject(EstateDataService)
  
  ngOnInit(): void {
    const addressEstate:Address=this.estateDataService.getAddress()
    if(addressEstate.latitude && addressEstate.longitude)
    {
      this.onCoordinateChanged({ lat: addressEstate.latitude, lon: addressEstate.longitude })
      this.onClickMarker({ lat: addressEstate.latitude, lon: addressEstate.longitude })
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
