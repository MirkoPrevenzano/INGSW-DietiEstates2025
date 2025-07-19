import { Injectable } from '@angular/core';
import { Address } from '../../../model/address';
import { Coordinate } from '../../../model/coordinate';
import { environment } from '../../../../environments/environment.prod';


@Injectable({
  providedIn: 'root'
})
export class AddressVerificationService {
  private readonly geoapifyKey = environment.geoapifyToken;

  async verifyAddress(address:Address): Promise<any> {
    const url = `https://api.geoapify.com/v1/geocode/search?
                  housenumber=${encodeURIComponent(address.houseNumber)}
                  &street=${encodeURIComponent(address.street)}
                  &postcode=${encodeURIComponent(address.postalCode)}
                  &city=${encodeURIComponent(address.city)}
                  &state=${encodeURIComponent(address.state)}
                  &country=${encodeURIComponent(address.country)}
                  &apiKey=${this.geoapifyKey}
                `;
    return fetch(url).then(result => result.json());
  }

  async verifyAddressToCoordinate(coordinates: Coordinate): Promise<any> {
    const url = `https://api.geoapify.com/v1/geocode/reverse?
                  &lat=${encodeURIComponent(coordinates.lat)}
                  &lon=${encodeURIComponent(coordinates.lon)}
                  &apiKey=${this.geoapifyKey}
                `;
    return fetch(url).then(result => result.json());
  }

  isEmptyField(address: Address): boolean {
    return !(address.street && 
      address.postalCode &&
      address.city && 
      address.state &&
      address.country)
  }

  displayAddressMessage(foundAddress: any, message: HTMLElement): void {
    if (foundAddress.properties.rank.confidence === 1) {
      message.textContent = `We verified the address you entered. The formatted address is: ${foundAddress.properties.formatted}`;
    } else if (foundAddress.properties.rank.confidence > 0.5 && foundAddress.properties.rank.confidence_street_level === 1) {
      message.textContent = `We have some doubts about the accuracy of the address: ${foundAddress.properties.formatted}`;
    } else if (foundAddress.properties.rank.confidence_street_level === 1) {
      message.textContent = `We can confirm the address up to street level: ${foundAddress.properties.formatted}`;
    } else {
      message.textContent = `We can only verify your address partially. The address we found is ${foundAddress.properties.formatted}`;
    }
  }

  
}