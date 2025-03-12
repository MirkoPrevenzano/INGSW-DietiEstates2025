import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild } from '@angular/core';
import { AutocompleteInputComponent } from '../autocomplete-input/autocomplete-input.component';
import { Address } from '../../model/address';
import { Coordinate } from '../../model/coordinate';
import { AddressVerificationService } from '../../_service/geoapify/address-verification/address-verification.service';
import { ToastrService } from 'ngx-toastr';
import { ButtonCustomComponent } from '../button-custom/button-custom.component';

@Component({
  selector: 'app-address-estates',
  templateUrl: './address-estates.component.html',
  styleUrls: ['./address-estates.component.scss'],
  imports:[
    AutocompleteInputComponent,
    ButtonCustomComponent
  ]
})
export class AddressEstatesComponent implements AfterViewInit, OnChanges {
  @ViewChild('streetInput') streetInput!: AutocompleteInputComponent;
  @ViewChild('cityInput') cityInput!: AutocompleteInputComponent;
  @ViewChild('countryInput') countryInput!: AutocompleteInputComponent;
  @ViewChild('stateInput') stateInput!: AutocompleteInputComponent;
  @ViewChild('postCodeInput') postCodeInput!: AutocompleteInputComponent;
  @ViewChild('houseNumberInput', { static: false }) houseNumberInput!: ElementRef<HTMLInputElement>;
  @Output() coordinatesChanged = new EventEmitter<Coordinate>()
  @Input() coordinatesToStreet?: Coordinate
  @Output() addressOut = new EventEmitter<Address>()

  constructor(
    private readonly addressVerificationService: AddressVerificationService,
    private readonly toastrService: ToastrService
  ) {}

  ngOnChanges(changes: SimpleChanges): void {
    if(changes['coordinatesToStreet'] && this.coordinatesToStreet){
      this.addressVerificationService.verifyAddressToCoordinate(
        this.coordinatesToStreet
      ).then(result=>{
        if(result)
          this.setAddress(result)   
      })
    }
  }

  /*da considerare come service i get e set*/
  setAddress(result: any) {
    let properties:any = result.features[0].properties
    this.cityInput.setValue(properties?.city ?? '')
    this.countryInput.setValue(properties?.country ?? '')
    this.stateInput.setValue(properties?.state ?? '')
    this.houseNumberInput.nativeElement.value = properties?.housenumber ?? ''
    this.postCodeInput.setValue(properties?.postcode ?? '')
    this.streetInput.setValue(properties?.street ?? '')
  }

  getAddress():Address{
    const houseNumber = parseInt(this.houseNumberInput.nativeElement.value ?? '', 10);
    const street = this.streetInput.getValue() ?? '';
    const state = this.stateInput.getValue() ?? '';
    const postalCode = this.postCodeInput.getValue() ?? '';
    const city = this.cityInput.getValue() ?? '';
    const country = this.countryInput.getValue() ?? '';

    return {
      houseNumber,
      street,
      state,
      postalCode,
      city,
      country
    }
  }

  ngAfterViewInit(): void {
    this.setupEventListeners();
  }

  private setupEventListeners(): void {
    this.streetInput.select.subscribe((street) => this.handleStreetSelect(street));
    this.cityInput.select.subscribe((city) => this.handleCitySelect(city));
    this.stateInput.select.subscribe((state) => this.handleStateSelect(state));
  }

  private handleStreetSelect(street: any): void {
    console.log(street)
    if (street) {
      this.streetInput.setValue(street.properties.street as string || '');
      this.houseNumberInput.nativeElement.value = street.properties.housenumber || '';
      this.stateInput.setValue(street.properties.state as string || '');
      this.countryInput.setValue(street.properties.country as string || '');
      this.postCodeInput.setValue(street.properties.postcode as string || '');
      this.cityInput.setValue(street.properties.city as string || '');
    }
  }

  private handleCitySelect(city: any): void {
    if (city) {
      this.cityInput.setValue(city.properties.city ?? '');
      this.countryInput.setValue(city.properties.country ?? '');
      this.stateInput.setValue(city.properties.state ?? '');
    }
  }

  private handleStateSelect(state: any): void {
    if (state) {
      this.stateInput.setValue(state.properties.state ?? '');
      this.countryInput.setValue(state.properties.country ?? '');
    }
  }

  
  checkAddress(): void {
   const message = document.getElementById('message') as HTMLElement;
   const address:Address = this.getAddress()

    if (this.addressVerificationService.isEmptyField(address)) {
      this.selectFieldWarning();
      return;
    }

    this.addressVerificationService.verifyAddress(address).then(result => {
      let features = result.features || [];
      const confidenceLevelToAccept = 0.25;
      features = features.filter((feature: { properties: { rank: { confidence: number; }; }; }) => feature.properties.rank.confidence >= confidenceLevelToAccept);

      if (features.length) {
        const foundAddress = features[0];
        this.displayCoordinates(foundAddress,address);

        this.addressVerificationService.displayAddressMessage(foundAddress, message);
      } else {
        message.textContent = "We cannot find your address. Please check if you provided the correct address.";
      }
    });
  }


  displayCoordinates(foundAddress: any, address:Address) {
    const lat = foundAddress.properties.lat
    const lon = foundAddress.properties.lon
    this.coordinatesChanged.emit({lat,lon})
    address.latitude= lat
    address.longitude= lon
    this.addressOut.emit(address)
  }

 

  private selectFieldWarning(): void {
    this.toastrService.warning("Please fill il all fields")
  }

  
}

