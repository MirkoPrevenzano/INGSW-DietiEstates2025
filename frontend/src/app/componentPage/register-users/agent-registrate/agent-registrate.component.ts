import { Component, inject } from '@angular/core';
import { RegisterComponent } from '../register/register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RegisterRequest } from '../../../model/registerRequest';
import { FormFieldComponent } from '../../../componentCustom/form-field/form-field.component';
import { CommonModule } from '@angular/common';
import { PasswordFieldComponent } from '../../../componentCustom/password-field/password-field.component';
import { Router } from '@angular/router';
import { ButtonCustomComponent } from '../../../componentCustom/button-custom/button-custom.component';
import { LoginWithGoogleComponent } from '../../../componentCustom/login-with-google/login-with-google.component';
import { CreateStaffService } from '../../../rest-backend/create-staff/create-staff.service';

@Component({
  selector: 'app-agent-registrate',
  imports: [
    ReactiveFormsModule,
    FormFieldComponent, 
    CommonModule,
    PasswordFieldComponent,
    ButtonCustomComponent,
    LoginWithGoogleComponent
  ],
  templateUrl: '../register/register.component.html',
})

export class AgentRegistrateComponent extends RegisterComponent{
  private readonly createStaffService = inject(CreateStaffService)
  private readonly router = inject(Router)
  override title: string="Create new agent";

  protected override onRegisterUser(userRequest: RegisterRequest): void {
    this.createStaffService.saveAgent(userRequest).subscribe({
      error: (err) => {
        if(err?.error.status >= 400 && err?.error.status < 500)
          this.notify.warning(err?.error.description)
        if(err?.error.status >= 500 && err?.error.status < 600)
          this.notify.error(err?.error.description)
      },
      complete:()=> {
        this.notify.success('agent add with success')
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

