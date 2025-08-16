import { Component, EventEmitter, inject, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CheckboxComponent } from '../checkbox/checkbox.component';
import { RealEstateBooleanFeatures } from '../../../model/request/support/realEstateBooleanFeatures';
import { EstateDataService } from '../../../_service/estate-data/estate-data.service';
import { estateFeatures } from '../../../constants/estate-features';

@Component({
  selector: 'app-estates-features-form',
  imports: [
    ReactiveFormsModule,
    CheckboxComponent],
  templateUrl: './estates-features-form.component.html',
  styleUrl: './estates-features-form.component.scss'
})
export class EstatesFeaturesFormComponent implements OnInit {
 
  @Output() estateFeatures = new EventEmitter<RealEstateBooleanFeatures>()

  private  readonly estateDataService: EstateDataService = inject(EstateDataService)

  featuresForm!:FormGroup
  estateFeaturesProperties= estateFeatures

  ngOnInit(): void {
    this.featuresForm = new FormGroup({
      elevator: new FormControl(false),
      concierge: new FormControl(false),
      airConditioning: new FormControl(false),
      terrace: new FormControl(false),
      garage: new FormControl(false),
      balcony: new FormControl(false),
      garden: new FormControl(false),
      swimmingPool: new FormControl(false),
      heating: new FormControl(false)
    })

    
    this.estateFeatures.emit(this.featuresForm.value as RealEstateBooleanFeatures)
    const estatesFeatures = this.estateDataService.getFeatures()

    if (estatesFeatures) {
      this.featuresForm.patchValue(estatesFeatures);
    }
    
    this.featuresForm.valueChanges.subscribe((value)=>{
      this.estateFeatures.emit(value as RealEstateBooleanFeatures)
    })

   
  }
}
