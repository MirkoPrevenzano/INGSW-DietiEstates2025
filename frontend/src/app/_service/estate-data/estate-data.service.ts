import { Injectable } from '@angular/core';
import { Address } from '../../model/request/support/address';
import { RealEstateMainFeatures } from '../../model/request/support/realEstateMainFeatures';
import { RealEstateBooleanFeatures } from '../../model/request/support/realEstateBooleanFeatures';


@Injectable({
  providedIn: 'root'
})
export class EstateDataService {

  constructor() { }
  private  address!:Address
  private  description!:RealEstateMainFeatures
  private  additionalFields: any
  private  features!: RealEstateBooleanFeatures

  getAddress():Address{
    return this.address || {} as Address;
  }

  getDescription():RealEstateMainFeatures{
    return this.description || {} as RealEstateMainFeatures;
  }

  getAdditionalFields():any{
    return this.additionalFields || {};
  }

  getFeatures(): RealEstateBooleanFeatures{
    return this.features || {
      heating: false,
      concierge: false,
      airConditioning: false,
      terrace: false,
      garage: false,
      balcony: false,
      garden: false,
      swimmingPool: false,
      elevator: false
    } as RealEstateBooleanFeatures;
  }

  setFeatures(features: RealEstateBooleanFeatures){
    this.features = features
  }

  setAdditionalFields(fields: any){
    this.additionalFields = fields
  }

  setDescription(description:RealEstateMainFeatures){
    this.description = description
  }

  setAddress(address:Address){
    this.address = address
  }
  

}
