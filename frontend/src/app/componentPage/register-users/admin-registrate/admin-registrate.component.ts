import { Component, inject } from '@angular/core';
import { RegisterComponent } from '../register/register.component';
import {  ReactiveFormsModule } from '@angular/forms';
import { RegisterValidationService } from '../../../_service/register-validation/register-validation.service';
import { FormFieldComponent } from '../../../componentCustom/form-field/form-field.component';
import { CommonModule } from '@angular/common';
import { PasswordFieldComponent } from '../../../componentCustom/password-field/password-field.component';
import {  ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { ButtonCustomComponent } from '../../../componentCustom/button-custom/button-custom.component';
import { LoginWithGoogleComponent } from '../../../componentCustom/login-with-google/login-with-google.component';
import { CollaboratorCreation } from '../../../model/request/collaboratorCreation';
import { HandleNotifyService } from '../../../_service/handle-notify.service';
import { AdministratorService } from '../../../rest-backend/administrator/administrator.service';

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
  private readonly administratorService = inject(AdministratorService)
  private readonly handleNotifyService = inject(HandleNotifyService)
  override title: string="Create new admin";

  constructor(
    protected override readonly registerValidation: RegisterValidationService,
    protected override readonly notify: ToastrService,
    private readonly router: Router 
  ){
    super(registerValidation, notify);
  }

  protected override onRegisterUser(userRequest: CollaboratorCreation): void {
    this.administratorService.saveAdmin(userRequest).subscribe({
      error: (err) => {
        this.handleNotifyService.showMessageError(err.error);
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
      const userRequest: CollaboratorCreation ={
        name:this.registerForm.value.name,
        surname: this.registerForm.value.lastname,
        username: this.registerForm.value.username,
      }
      this.onRegisterUser(userRequest)
    }
    
      
  }

  


  
}
