import { Component, EventEmitter, inject, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CheckboxComponent } from '../checkbox/checkbox.component';
import { EstateFeatures } from '../../../model/estateFeatures';
import { EstateDataService } from '../../../_service/estate-data.service';

@Component({
  selector: 'app-estates-features-form',
  imports: [ReactiveFormsModule, CheckboxComponent],
  templateUrl: './estates-features-form.component.html',
  styleUrl: './estates-features-form.component.scss'
})
export class EstatesFeaturesFormComponent implements OnInit {
 
  @Output() estateFeatures = new EventEmitter<EstateFeatures>()

  private  readonly estateDataService: EstateDataService = inject(EstateDataService)

  featuresForm!:FormGroup

  



  ngOnInit(): void {

    this.featuresForm = new FormGroup({
      hasElevator: new FormControl(false),
      hasConcierge: new FormControl(false),
      hasAirConditioning: new FormControl(false),
      hasTerrace: new FormControl(false),
      hasGarage: new FormControl(false),
      hasBalcony: new FormControl(false),
      hasGarden: new FormControl(false),
      hasSwimmingPool: new FormControl(false),
      hasHeating: new FormControl(false)
    })

    
    this.estateFeatures.emit(this.featuresForm.value as EstateFeatures)
    const estatesFeatures = this.estateDataService.getFeatures()

    if (estatesFeatures) {
      this.featuresForm.patchValue(estatesFeatures);
    }
    
    this.featuresForm.valueChanges.subscribe((value)=>{
      this.estateFeatures.emit(value as EstateFeatures)
    })

   
  }
}
