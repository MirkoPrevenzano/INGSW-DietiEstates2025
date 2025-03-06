import { Injectable } from '@angular/core';
import { Address } from '../model/address';
import { EstateFeatures } from '../model/estateFeatures';
import { EstateDescribe } from '../model/estateDescribe';

@Injectable({
  providedIn: 'root'
})
export class EstateDataService {

  constructor() { }
  private  address!:Address
  private  description!:EstateDescribe
  private  additionalFields: any
  private  features!: EstateFeatures

  getAddress():Address{
    return this.address
  }

  getDescription():EstateDescribe{
    return this.description
  }

  getAdditionalFields():any{
    return this.additionalFields
  }

  getFeatures(): EstateFeatures{
    return this.features
  }

  setFeatures(features: EstateFeatures){
    this.features = features
  }

  setAdditionalFields(fields: any){
    this.additionalFields = fields
  }

  setDescription(description:EstateDescribe){
    this.description = description
  }

  setAddress(address:Address){
    this.address = address
  }
  

}
