import { Component, EventEmitter, inject, Input, OnInit, Output } from '@angular/core';
import {  FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { EstateDescribe } from '../../../model/estateDescribe';
import { FormFieldComponent } from '../../form-field/form-field.component';
import { CommonModule } from '@angular/common';
import { UploadPhotosComponent } from '../../upload-photos/upload-photos.component';
import { ESTATE_TYPES_CONFIG } from '../../../configuration/estate-types.config';
import { EstateType } from '../../../model/estateType';
import { EstateDataService } from '../../../_service/estate-data/estate-data.service';

@Component({
  selector: 'app-description-estates-form',
  imports: [
    ReactiveFormsModule,
    FormFieldComponent, 
    UploadPhotosComponent,
    CommonModule
  ],
  templateUrl: './description-estates-form.component.html',
  styleUrl: './description-estates-form.component.scss'
})
export class DescriptionEstatesFormComponent implements OnInit{
  @Input() estateType!: EstateType;
  @Output() formSubmit =new EventEmitter<{estate:EstateDescribe, additionalFields:any}>()
  dynamicFields: any[]= []
  featuresForm!: FormGroup
  additionalFields: any = {};
  formChecked: boolean = false
  maxTitle: number = 35
  minDescription: number = 250



  @Output() imagesOut= new EventEmitter<string[]>() 
  @Output() fileOut= new EventEmitter<File[]>() 



  private readonly estateDataService  = inject(EstateDataService)


  onPhotosChanged(photos:string[]): void {
    this.imagesOut.emit(photos)
  }

  onFileChanged(photos: File[]){
    this.fileOut.emit(photos)
  }



  ngOnInit(): void {
  
    this.featuresForm = new FormGroup({
        title: new FormControl('', Validators.required),
        description: new FormControl('',Validators.required),
        price: new FormControl(0),
        size: new FormControl(0.0,),
        roomsNumber: new FormControl(0),
        floorNumber: new FormControl(0),
        energyClass: new FormControl('',Validators.required),
        parkingSpacesNumber: new FormControl(0),
        condoFee: new FormControl(0.0),
        furnitureCondition: new FormControl('',Validators.required),
        estateCondition: new FormControl('',Validators.required)
    })

    this.loadDynamicFields()

    this.sendDateForm()

    this.loadOptionalDate()
  
   
  }

  isValid(){
    if( this.featuresForm.valid ){
      this.formChecked=false
      return true
    }
    this.formChecked = true
    return false
  }



  private sendDateForm() {
    this.featuresForm.valueChanges.subscribe(value => {
      const estate: EstateDescribe={
        title: value.title,
        description: value.description,
        price: value.price,
        size: value.size,
        roomsNumber: value.roomsNumber,
        floorNumber: value.floorNumber,
        energyClass: value.energyClass,
        parkingSpacesNumber: value.parkingSpacesNumber,
        condoFee: value.condoFee,
        furnitureCondition: value.furnitureCondition,
        estateCondition: value.estateCondition
      }


      this.additionalFields = {};
      this.dynamicFields.forEach(field => {
        this.additionalFields[field.formControlName] = value[field.formControlName];
      });

      this.formSubmit.emit({estate, additionalFields:this.additionalFields})
    })
  }
 
 
  loadDynamicFields():void{
    this.dynamicFields = ESTATE_TYPES_CONFIG[this.estateType]
    this.dynamicFields.forEach(field => {
      this.featuresForm.addControl(field.formControlName, new FormControl('', Validators.required));
    });  
  }

  private  loadOptionalDate() {
    const savedDescription = this.estateDataService.getDescription()
    const savedAdditionalFields = this.estateDataService.getAdditionalFields()

    if(savedDescription)
      this.featuresForm.patchValue(savedDescription)
    if(savedAdditionalFields){
      this.dynamicFields.forEach(field =>{
        if(savedAdditionalFields[field.formControlName]){
          this.featuresForm.get(field.formControlName)?.setValue(
            savedAdditionalFields[field.formControlName]
          )
        }
      })
    }
  }
  
    
}


