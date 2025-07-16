

import { Component, Output, EventEmitter, ViewChild, AfterViewInit } from '@angular/core';
import { FormFieldComponent } from '../../../componentCustom/form-field/form-field.component';
import { CheckboxComponent } from '../../create-estates-components/checkbox/checkbox.component';
import { FormGroup, FormControl, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AutocompleteInputComponent } from '../../../componentCustom/autocomplete-input/autocomplete-input.component';
import { Coordinate } from '../../../model/coordinate';
import { ActivatedRoute } from '@angular/router';
import { estateFeatures } from '../../../constants/estate-features';
import { estateLocationFeatures } from '../../../constants/estate-location-features';
import { AddressVerificationService } from '../../../_service/geoapify/address-verification/address-verification.service';
@Component({
  selector: 'app-estate-filters',
  imports: [
    FormFieldComponent, 
    CheckboxComponent,
    ReactiveFormsModule,
    CommonModule,
    AutocompleteInputComponent,
  ],
  templateUrl: './estate-filters.component.html',
  styleUrl: './estate-filters.component.scss'
})
export class EstateFiltersComponent implements AfterViewInit{
    ngAfterViewInit(): void {
      this.setupComponent();
    }

    constructor(
      private readonly route: ActivatedRoute,
      private readonly addressVerification: AddressVerificationService
    ){}


    setupComponent(): void {
      this.localityInput.select.subscribe((locality)=>this.handleLocalitySelect(locality))
      this.route.queryParams.subscribe((params) => {
        const lat = parseFloat(params['lat']);
        const lon = parseFloat(params['lon']);
    
        if (!isNaN(lat) && !isNaN(lon)) {
          this.coordinate.lat = lat;
          this.coordinate.lon = lon;
    
          this.addressVerification.verifyAddressToCoordinate(this.coordinate).then((locality) => {
            if (locality) {
              this.localityInput.setValue(locality.features[0].properties.city);
            }
          });
        }
      });
    }

    handleLocalitySelect(locality: any): void {
      this.localityInput.setValue(locality.properties.formatted)
      this.coordinate.lat = locality.geometry.coordinates[1]
      this.coordinate.lon = locality.geometry.coordinates[0]
    }

    searchForm!:FormGroup
    coordinate: Coordinate = {lat:NaN, lon:NaN}
    radius:number =0
    @Output() filterParams: EventEmitter<{ [key: string]: any }> = new EventEmitter();
  
    estateFeatures = estateFeatures;
    estateLocationFeatures = estateLocationFeatures;
  

    @ViewChild('localityInput') localityInput!: AutocompleteInputComponent
  
    ngOnInit(): void {
      this.searchForm = new FormGroup({
        type: new FormControl('For Sale'),
        minPrice: new FormControl(0),
        maxPrice: new FormControl(0),
        rooms: new FormControl(0),
        energyClass: new FormControl(''),
        heating: new FormControl(false),
        concierge: new FormControl(false),
        airConditioning: new FormControl(false),
        terrace: new FormControl(false),
        garage: new FormControl(false),
        balcony: new FormControl(false),
        garden: new FormControl(false),
        swimmingPool: new FormControl(false),
        elevator: new FormControl(false),
        isNearPark: new FormControl(false),
        isNearPublicTransport: new FormControl(false),
        isNearSchool: new FormControl(false),
        radius: new FormControl(100)
      });
      
      this.route.queryParams.subscribe(params => {
        for (const key in params) {
          if (this.searchForm.controls[key]) {
            this.searchForm.controls[key].setValue(params[key]);
          }
        }
      });

    }
    
    onRadiusChange(event: Event): void {
      const inputElement = event.target as HTMLInputElement;
      const radiusValue = parseInt(inputElement.value, 10);
      this.searchForm.controls['radius'].setValue(radiusValue);
    }

    onSearch(): void {
      const queryParams: { [key: string]: any } = {};
      if (this.searchForm.valid) {
        const searchParams = this.searchForm.value;
          for (const key in searchParams) {
            if (searchParams[key] && searchParams[key] !== 'Anything') {
              queryParams[key] = searchParams[key];
          }
        } 
      }

      if(this.coordinate.lat && this.coordinate.lon){
        queryParams['lat']= this.coordinate.lat
        queryParams['lon']= this.coordinate.lon
      }

      if(Object.keys(queryParams).length)
      {
        this.filterParams.emit(queryParams)
      }
    }


    onReset(){
      this.searchForm.reset({
        type: '',
        minPrice: 0,
        maxPrice: 0,
        rooms: 0,
        energyClass: '',
        hasHeating: false,
        hasConcierge: false,
        hasAirConditioning: false,
        hasTerrace: false,
        hasGarage: false,
        hasBalcony: false,
        hasGarden: false,
        hasSwimmingPool: false,
        hasElevator: false,
        isNearPark: false,
        isNearPublicTransport: false,
        isNearSchool: false
      });
      this.coordinate = { lat: NaN, lon: NaN };
    }
  }
