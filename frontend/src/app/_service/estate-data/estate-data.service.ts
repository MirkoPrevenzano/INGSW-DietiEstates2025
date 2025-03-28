import { Injectable } from '@angular/core';
import { Address } from '../../model/address';
import { EstateDescribe } from '../../model/estateDescribe';
import { EstateFeatures } from '../../model/estateFeatures';


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
