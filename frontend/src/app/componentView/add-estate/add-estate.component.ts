import { Component, EventEmitter, Output } from '@angular/core';
import { FormFieldComponent } from '../form-field/form-field.component';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonCustomComponent } from '../button-custom/button-custom.component';

@Component({
  selector: 'app-choose-type-estate',
  imports: [
    FormFieldComponent,
    ReactiveFormsModule,
    ButtonCustomComponent
  ],
  templateUrl: './add-estate.component.html',
  styleUrl: './add-estate.component.scss'
})
export class AddEstateComponent {
  @Output() typeEstate =new EventEmitter<string>()

  typeForm= new FormGroup({
    listingType: new FormControl('For Sale', Validators.required)
  })

  onSubmit(): void {
    if (this.typeForm.valid) {
      const listingType = this.typeForm.get('listingType')?.value;
      this.typeEstate.emit(listingType!)
    }
  }
  
}
