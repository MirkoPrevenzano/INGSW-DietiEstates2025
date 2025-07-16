import { Injectable } from '@angular/core';
import { Address } from '../../model/address';
import { DescriptionEstatesFormComponent } from '../../componentPage/create-estates-components/description-estates-form/description-estates-form.component';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ValidateStepEstateCreateService {
  
  
  readonly errorEmptyFields = "Please, compile all fields"
  errorMorePhotos = "Sorry, number max of photos is"
  errorLessPhotos = "Sorry, number min of photos is"
  readonly successMessage = ""

  checkStep1(address: Address):string {
    if(!address)
      return this.errorEmptyFields + " and push 'check address'"
    return this.successMessage;
  }

  checkStep2(
    descriptionFormComponent: DescriptionEstatesFormComponent, 
    length: number, 
    maxPhotos: number, 
    minPhotos: number
  ): string {
    let error =""
    if(!descriptionFormComponent.isValid())
      error+=this.errorEmptyFields+"\n" 
    if(length<0)
      error+=this.errorLessPhotos+" "+minPhotos+"\n"
    else if(length>maxPhotos)
      error+=this.errorMorePhotos+" "+maxPhotos+"\n"
    return error
  }

  checkStep3(featuresForm: FormGroup<any>) {
    if(!featuresForm.valid)
      return this.errorEmptyFields
    return this.successMessage
  }

  constructor() { }
}
