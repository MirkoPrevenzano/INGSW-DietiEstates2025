import { Injectable } from '@angular/core';
import { GeocoderAutocomplete, LocationType } from '@geoapify/geocoder-autocomplete';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GeocoderAutocompleteService {
  private readonly geoapifyKey = environment.geoapifyToken;

  createAutocomplete(
    elementId: string, 
    type: string, 
    allowNonVerifiedHouseNumber: boolean = false, 
    allowNonVerifiedStreet: boolean = false
  ): GeocoderAutocomplete {
    const element= document.getElementById(elementId)
    if(!element)
      throw new Error(`Element with id "${elementId}"`)
    return new GeocoderAutocomplete(
      element,
      this.geoapifyKey,
      {
        type: type as LocationType,
        allowNonVerifiedHouseNumber,
        allowNonVerifiedStreet,
        skipIcons: true,
        placeholder: `Enter ${type}`
      }
    );
  }
}