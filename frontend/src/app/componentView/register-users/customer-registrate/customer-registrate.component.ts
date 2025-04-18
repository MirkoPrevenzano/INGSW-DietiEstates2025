import { Component } from '@angular/core';
import { RegisterComponent } from '../register/register.component';
import { RegisterValidationService } from '../../../_service/register-validation/register-validation.service';
import { RegisterService } from '../../../_service/rest-backend/register/register.service';
import { ReactiveFormsModule } from '@angular/forms';
import { RegisterRequest } from '../../../model/registerRequest';
import { CommonModule } from '@angular/common';
import { FormFieldComponent } from '../../form-field/form-field.component';
import { PasswordFieldComponent } from '../../password-field/password-field.component';
import { Router, RouterModule } from '@angular/router';
import {  ToastrService } from 'ngx-toastr';
import { HttpErrorResponse } from '@angular/common/http';
import { ButtonCustomComponent } from '../../button-custom/button-custom.component';
import { LoginWithGoogleComponent } from '../../login-with-google/login-with-google.component';

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
    if(this.registerValidation.isEmailInvalid(this.registerForm!.value.username))
    {
      this.notify.warning('E-mail not valid')
    }
    else{
      this.register.registrate(userRequest).subscribe({
        error: (err: HttpErrorResponse) => {
          const errorMessage = err.error;
          const errorHeaders = err.headers.get('expires');
          console.log(err)
          this.notify.error(`Error: ${errorMessage}`);
          console.log('Error Headers:', errorHeaders);
        },
        complete: ()=>{
          this.router.navigateByUrl('login')
        }
      })
    }
  }

  
  
}

