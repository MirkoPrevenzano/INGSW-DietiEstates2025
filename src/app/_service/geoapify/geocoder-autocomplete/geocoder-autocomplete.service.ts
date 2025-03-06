import { Injectable } from '@angular/core';
import { GeocoderAutocomplete, LocationType } from '@geoapify/geocoder-autocomplete';

@Injectable({
  providedIn: 'root'
})
export class GeocoderAutocompleteService {
  private readonly geoapifyKey = 'c0324c2c2afb488980eae981c7906a43';

  createAutocomplete(elementId: string, type: string, allowNonVerifiedHouseNumber: boolean = false, allowNonVerifiedStreet: boolean = false): GeocoderAutocomplete {
    return new GeocoderAutocomplete(
      document.getElementById(elementId) as HTMLElement,
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