import { Component, Output, EventEmitter, ViewChild, AfterViewInit } from '@angular/core';
import { FormFieldComponent } from '../form-field/form-field.component';
import { CheckboxComponent } from '../create-estates-components/checkbox/checkbox.component';
import { FormGroup, FormControl, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AutocompleteInputComponent } from '../autocomplete-input/autocomplete-input.component';
import { Coordinate } from '../../model/coordinate';
import { ActivatedRoute } from '@angular/router';

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
      this.localityInput.select.subscribe((locality)=>this.handleLocalitySelect(locality))
    }

    constructor(
      private readonly route: ActivatedRoute
    ){}

    handleLocalitySelect(locality: any): void {
      this.coordinate.lat = locality.geometry.coordinates[0]
      this.coordinate.lon = locality.geometry.coordinates[1]
    }

    searchForm!:FormGroup
    coordinate: Coordinate = {lat:NaN, lon:NaN}
    radius:number =0
    @Output() filterParams: EventEmitter<{ [key: string]: any }> = new EventEmitter();
  
    estateFeatures = [
      { controlName: 'hasHeating', label: 'Heating' },
      { controlName: 'hasConcierge', label: 'Concierge' },
      { controlName: 'hasAirConditioning', label: 'Air Conditioning' },
      { controlName: 'hasTerrace', label: 'Terrace' },
      { controlName: 'hasGarage', label: 'Garage' },
      { controlName: 'hasBalcony', label: 'Balcony' },
      { controlName: 'hasGarden', label: 'Garden' },
      { controlName: 'hasSwimmingPool', label: 'Swimming Pool' },
      { controlName: 'hasElevator', label: 'Elevator' }
    ];
  
    estateLocationFeatures = [
      { controlName: 'isNearPark', label: 'Is Near Park' },
      { controlName: 'isNearSchool', label: 'Is Near School' },
      { controlName: 'isNearPublicTransport', label: 'Is Near Public Transport' },

    ];
  

    @ViewChild('localityInput') localityInput!: AutocompleteInputComponent
    //constructor(private estateService: EstateService, private router: Router) {}
  
    ngOnInit(): void {
      this.searchForm = new FormGroup({
        type: new FormControl(''),
        minPrice: new FormControl(0),
        maxPrice: new FormControl(0),
        rooms: new FormControl(0),
        energyClass: new FormControl(''),
        hasHeating: new FormControl(false),
        hasConcierge: new FormControl(false),
        hasAirConditioning: new FormControl(false),
        hasTerrace: new FormControl(false),
        hasGarage: new FormControl(false),
        hasBalcony: new FormControl(false),
        hasGarden: new FormControl(false),
        hasSwimmingPool: new FormControl(false),
        hasElevator: new FormControl(false),
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

        if (params['lat'] && params['lon']) {
          this.coordinate.lat = parseFloat(params['lat']);
          this.coordinate.lon = parseFloat(params['lon']);
        }
      })
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
  
        // Aggiungi solo i parametri che hanno un valore
        for (const key in searchParams) {
          if (searchParams[key]) {
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

        //devo emettere da qui i risultati
        /*this.estateService.searchEstates(queryParams).subscribe(
          (response) => {
            console.log('Search Results:', response);
            
          },
          (error) => {
            console.error('Error:', error);
          }
        );*/
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
