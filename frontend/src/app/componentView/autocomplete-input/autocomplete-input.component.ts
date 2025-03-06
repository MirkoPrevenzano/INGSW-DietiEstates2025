import { Component, Input, AfterViewInit, Output, EventEmitter } from '@angular/core';
import { GeocoderAutocomplete } from '@geoapify/geocoder-autocomplete';
import { GeocoderAutocompleteService } from '../../_service/geoapify/geocoder-autocomplete/geocoder-autocomplete.service';

@Component({
  selector: 'app-autocomplete-input',
  template: `
    <div class="mb-6 relative">
      <label [for]="elementId" class="block text-sm font-medium text-gray-700">{{ label }}:</label>
      <div [id]="elementId" class="address-field relative mt-2 w-full   rounded-lg"></div>
    </div>
  `,
  styleUrls: ['./autocomplete-input.component.scss']
})
export class AutocompleteInputComponent implements AfterViewInit {
  @Input() elementId!: string;
  @Input() type!: string;
  @Input() label!: string;
  @Output() select = new EventEmitter<any>();

  constructor(
    private readonly geocoderAutocompleteService:GeocoderAutocompleteService
  ){}

  private readonly geoapifyKey = 'c0324c2c2afb488980eae981c7906a43';
  private autocomplete!: GeocoderAutocomplete;
  
  ngAfterViewInit(): void {
    this.autocomplete = 
      this.geocoderAutocompleteService.createAutocomplete(
        this.elementId,
        this.type,
        true,
        true
      )
    this.autocomplete.on('select', (result) => this.select.emit(result));
  }

  setValue(value: string): void {
    this.autocomplete.setValue(value);
  }

  getValue(): string {
    return this.autocomplete.getValue();
  }
}