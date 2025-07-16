import { Component } from '@angular/core';
import { RegisterComponent } from '../register/register.component';
import { RegisterValidationService } from '../../../_service/register-validation/register-validation.service';
import { RegisterService } from '../../../rest-backend/register/register.service';
import { ReactiveFormsModule } from '@angular/forms';
import { RegisterRequest } from '../../../model/registerRequest';
import { CommonModule } from '@angular/common';
import { FormFieldComponent } from '../../../componentCustom/form-field/form-field.component';
import { PasswordFieldComponent } from '../../../componentCustom/password-field/password-field.component';
import { Router, RouterModule } from '@angular/router';
import {  ToastrService } from 'ngx-toastr';
import { HttpErrorResponse } from '@angular/common/http';
import { ButtonCustomComponent } from '../../../componentCustom/button-custom/button-custom.component';
import { LoginWithGoogleComponent } from '../../../componentCustom/login-with-google/login-with-google.component';

@Component({
  selector: 'app-customer-registrate',
  imports: [
    ReactiveFormsModule, 
    CommonModule,
    FormFieldComponent,
    PasswordFieldComponent,
    RouterModule,
    ButtonCustomComponent,
    LoginWithGoogleComponent
  ],
  templateUrl: '../register/register.component.html',
  styleUrl: './customer-registrate.component.scss'
})
export class CustomerRegistrateComponent extends RegisterComponent{
  override title: string="Register";
  constructor(
    private readonly register: RegisterService,
    protected override readonly registerValidation: RegisterValidationService,
    protected override readonly notify: ToastrService,
    private readonly router: Router
  ) {
    super(registerValidation, notify);
  }
  
  protected override onRegisterUser(userRequest: RegisterRequest): void {
    const username = this.registerForm.value.username ?? ''
    if(this.registerValidation.isEmailInvalid(username))
    {
      this.notify.warning('E-mail not valid')
    }
    else{
      this.register.registrate(userRequest).subscribe({
        error: (err) => {
          if(err?.error.status >= 400 && err?.error.status < 500)
            this.notify.warning(err?.error.description)
          if(err?.error.status >= 500 && err?.error.status < 600)
            this.notify.error(err?.error.description)
        },
        complete: ()=>{
          this.router.navigateByUrl('login')
        }
      })
    }
  }

  
  
}

