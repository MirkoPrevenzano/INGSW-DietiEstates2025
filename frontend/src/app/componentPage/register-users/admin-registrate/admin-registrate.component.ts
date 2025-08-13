import { Component, inject } from '@angular/core';
import { RegisterComponent } from '../register/register.component';
import { RegisterRequest } from '../../../model/registerRequest';
import {  ReactiveFormsModule } from '@angular/forms';
import { RegisterValidationService } from '../../../_service/register-validation/register-validation.service';
import { FormFieldComponent } from '../../../componentCustom/form-field/form-field.component';
import { CommonModule } from '@angular/common';
import { PasswordFieldComponent } from '../../../componentCustom/password-field/password-field.component';
import {  ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { ButtonCustomComponent } from '../../../componentCustom/button-custom/button-custom.component';
import { LoginWithGoogleComponent } from '../../../componentCustom/login-with-google/login-with-google.component';
import { CreateStaffService } from '../../../rest-backend/create-staff/create-staff.service';

@Component({
  selector: 'app-admin-registrate',
  imports: [
    ReactiveFormsModule,
    FormFieldComponent, 
    CommonModule,
    PasswordFieldComponent,
    ButtonCustomComponent,
    LoginWithGoogleComponent
    
  ],
  templateUrl: '../register/register.component.html',
  styleUrl: './admin-registrate.component.scss'
})
export class AdminRegistrateComponent extends RegisterComponent{
  private readonly createStaffService = inject(CreateStaffService)
  override title: string="Create new admin";

  constructor(
    protected override readonly registerValidation: RegisterValidationService,
    protected override readonly notify: ToastrService,
    private readonly router: Router 
  ){
    super(registerValidation, notify);
  }

  protected override onRegisterUser(userRequest: RegisterRequest): void {
    this.createStaffService.saveAdmin(userRequest).subscribe({
      error: (err) => {
        if(err?.error.status >= 400 && err?.error.status < 500)
          this.notify.warning(err?.error.description)
        if(err?.error.status >= 500 && err?.error.status < 600)
          this.notify.error(err?.error.description)
      },
      complete: ()=>{
        this.notify.success('Admin created successfully')
        setTimeout(()=>{
          this.router.navigateByUrl('home/admin')
        },1000)
      }
    })
  }

  override onRegister()
  {
    if(this.registerValidation.isEmailInvalid(this.registerForm.value.username))
      this.notify.warning("Email is not valid")
    else{
      const userRequest: RegisterRequest ={
        name:this.registerForm.value.name,
        surname: this.registerForm.value.lastname,
        username: this.registerForm.value.username,
      }
      this.onRegisterUser(userRequest)
    }
    
      
  }

  


  
}
