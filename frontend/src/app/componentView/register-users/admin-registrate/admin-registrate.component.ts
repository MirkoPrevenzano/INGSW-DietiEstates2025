import { Component, inject } from '@angular/core';
import { RegisterComponent } from '../register/register.component';
import { RegisterRequest } from '../../../model/registerRequest';
import { SavePersonalService } from '../../../_service/rest-backend/save-personal/save-personalservice';
import {  ReactiveFormsModule } from '@angular/forms';
import { RegisterValidationService } from '../../../_service/register-validation/register-validation.service';
import { FormFieldComponent } from '../../form-field/form-field.component';
import { CommonModule } from '@angular/common';
import { PasswordFieldComponent } from '../../password-field/password-field.component';
import {  ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { ButtonCustomComponent } from '../../button-custom/button-custom.component';

@Component({
  selector: 'app-admin-registrate',
  imports: [
    ReactiveFormsModule,
    FormFieldComponent, 
    CommonModule,
    PasswordFieldComponent,
    ButtonCustomComponent
    
  ],
  templateUrl: '../register/register.component.html',
  styleUrl: './admin-registrate.component.scss'
})
export class AdminRegistrateComponent extends RegisterComponent{
  private readonly savePersonalService = inject(SavePersonalService)
  override title: string="Create new admin";

  constructor(
    protected override readonly registerValidation: RegisterValidationService,
    protected override readonly notify: ToastrService,
    private readonly router: Router 
  ){
    super(registerValidation, notify);
  }

  protected override onRegisterUser(userRequest: RegisterRequest): void {
    this.savePersonalService.saveAdmin(userRequest).subscribe({
      error: (err)=>{
        this.notify.warning(err.headers.get('error'))
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
    const userRequest: RegisterRequest ={
        name:this.registerForm.value.name,
        surname: this.registerForm.value.lastname,
        username: this.registerForm.value.username,
    }
    console.log(userRequest)
    this.onRegisterUser(userRequest)
      
  }

  


  
}
