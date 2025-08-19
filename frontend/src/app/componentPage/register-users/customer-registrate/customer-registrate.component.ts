import { Component, inject } from '@angular/core';
import { RegisterComponent } from '../register/register.component';
import { RegisterValidationService } from '../../../_service/register-validation/register-validation.service';
import { AuthenticationService } from '../../../rest-backend/authentication/authentication.service';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FormFieldComponent } from '../../../componentCustom/form-field/form-field.component';
import { PasswordFieldComponent } from '../../../componentCustom/password-field/password-field.component';
import { Router, RouterModule } from '@angular/router';
import {  ToastrService } from 'ngx-toastr';
import { ButtonCustomComponent } from '../../../componentCustom/button-custom/button-custom.component';
import { LoginWithGoogleComponent } from '../../../componentCustom/login-with-google/login-with-google.component';
import { CustomerRegistration } from '../../../model/request/customerRegistration';
import { HandleNotifyService } from '../../../_service/handle-notify.service';

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
  private readonly handleError= inject(HandleNotifyService)
  override title: string="Register";
  constructor(
    private readonly register: AuthenticationService,
    protected override readonly registerValidation: RegisterValidationService,
    protected override readonly notify: ToastrService,
    private readonly router: Router
  ) {
    super(registerValidation, notify);
  }
  
  protected override onRegisterUser(userRequest: CustomerRegistration): void {   
    this.register.registrate(userRequest).subscribe({
      error: (err) => {
        this.handleError.showMessageError(err.error)
      },
      complete: ()=>{
        this.router.navigateByUrl('login')
      }
    })
  }
  
}

