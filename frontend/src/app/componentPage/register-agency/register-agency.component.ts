import { Component } from '@angular/core';
import { FormControl, FormControlName, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AgencyRegistration } from '../../model/request/agencyRegistration';
import { AgencyService } from '../../rest-backend/agency/agency.service';
import { HandleNotifyService } from '../../_service/handle-notify.service';
import { ToastrService } from 'ngx-toastr';
import { FormFieldComponent } from '../../componentCustom/form-field/form-field.component';
import { CommonModule } from '@angular/common';
import { PasswordFieldComponent } from '../../componentCustom/password-field/password-field.component';
import { ButtonCustomComponent } from '../../componentCustom/button-custom/button-custom.component';
import { RegisterValidationService } from '../../_service/register-validation/register-validation.service';
import { PasswordValidatorService } from '../../_service/password-validator/password-validator.service';

@Component({
  selector: 'app-register-agency',
  imports: [
    FormFieldComponent,
    ReactiveFormsModule, 
    CommonModule,
    PasswordFieldComponent,
    RouterModule,
    ButtonCustomComponent
  ],
  templateUrl: './register-agency.component.html',
  styleUrl: './register-agency.component.scss'
})
export class RegisterAgencyComponent {
  form!: FormGroup

  constructor(
    private readonly agencyService: AgencyService,
    private readonly router: Router,
    private readonly notifyService: ToastrService,
    private readonly handleNotifyService: HandleNotifyService,
    private readonly registerValidationService: RegisterValidationService,
    private readonly passwordValidator: PasswordValidatorService

  ){
    this.form = new FormGroup({
      agencyName: new FormControl('', Validators.required),
      businessName: new FormControl('', Validators.required),
      vatNumber: new FormControl('', Validators.required),
      name: new FormControl('', Validators.required),
      surname: new FormControl('', Validators.required),
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    })
  }

  onSubmit(){
    if (this.form.valid) {
      const registerAgency: AgencyRegistration = {
        businessName: this.form.value.businessName,
        agencyName: this.form.value.agencyName,
        vatNumber: this.form.value.vatNumber,
        name: this.form.value.name,
        surname: this.form.value.surname,
        username: this.form.value.username,
        password: this.form.value.password
      }

      if(this.registerValidationService.isEmailInvalid(this.form.value.username)){
        this.notifyService.warning("Email is not valid")
        return;
      }
      const message:string[] = this.passwordValidator.validatePassword(this.form.value.password)
      if(message.length > 0){
        this.notifyService.warning(message.join('\n '))
        return;
      }

      this.agencyService.registerAgency(registerAgency).subscribe({
        next: (resp) => {
          this.notifyService.success('Agency registered successfully!', 'Success');
          setTimeout(() => {
            this.router.navigateByUrl('/login');
          }, 1000);
        },
        error: (err) => {
          this.handleNotifyService.showMessageError(err.error);
        }
      })
    } else {
      this.notifyService.warning('Please fill in all required fields', 'Form Invalid');
    }
  }
}
