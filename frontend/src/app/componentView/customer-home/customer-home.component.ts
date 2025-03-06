import { Component, ViewChild } from '@angular/core';
import { EstateType } from '../../model/estateType';
import { AutocompleteInputComponent } from '../autocomplete-input/autocomplete-input.component';
import { FormFieldComponent } from '../form-field/form-field.component';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MapPopupComponent } from '../map-popup/map-popup.component';
import { Coordinate } from '../../model/coordinate';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-customer-home',
  imports:[
    AutocompleteInputComponent, 
    FormFieldComponent
  ],
  templateUrl: './customer-home.component.html',
  styleUrls: ['./customer-home.component.scss']
})
export class CustomerHomeComponent{
  
  @ViewChild('localityInput') localityInput!:AutocompleteInputComponent
  selectedType: EstateType = 'For Sale';
  radius: number = 5000
  coordinate: Coordinate = {lat:NaN, lon:NaN}

  constructor(
    private readonly matDialog: MatDialog,
    private readonly router: Router,
    private readonly toastrService: ToastrService
  ){}

  formSelectType = new FormGroup(
    {
      estateType: new FormControl('For Sale')
    }
  )

  
  
  

  onLocationSelected(locality: any) {
    console.log(locality)
    this.coordinate.lat= locality.geometry.coordinates[0]
    this.coordinate.lon= locality.geometry.coordinates[1]
  }

  openMap() {
    const dialog =this.matDialog.open(MapPopupComponent,{
      minWidth:"50vw" ,
      minHeight:"24vh",
      disableClose: false,
      hasBackdrop: true,
      
    })

    dialog.afterClosed().subscribe(result=>{
      if(result){
        this.radius = result.radius
        this.coordinate = result.coordinate;

        this.submit()
      }
    })
  }

  submit(){

    if (!this.coordinate.lat) {
      this.toastrService.warning("Please select a location before proceeding.");
      return;
    }
    console.log(this.selectedType)
    const queryParams = {
      type: this.selectedType,
      lat: this.coordinate.lat,
      lon: this.coordinate.lon,
      radius: this.radius,
      
    };
    

    this.router.navigate(['/container'], { queryParams });
  }  
  
}