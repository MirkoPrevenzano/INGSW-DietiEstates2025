import { Component, inject } from '@angular/core';
import { RegisterComponent } from '../register/register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RegisterRequest } from '../../../model/registerRequest';
import { FormFieldComponent } from '../../form-field/form-field.component';
import { CommonModule } from '@angular/common';
import { PasswordFieldComponent } from '../../password-field/password-field.component';
import { Router } from '@angular/router';
import { ButtonCustomComponent } from '../../button-custom/button-custom.component';
import { LoginWithGoogleComponent } from '../../login-with-google/login-with-google.component';
import { CreateStaffService } from '../../../_service/rest-backend/create-staff/create-staff.service';

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
      error: (err)=>{
        console.log(err)
        this.notify.error(err.headers.get('error'))
      },
      complete:()=> {
        this.notify.success('agent add with success')
        setTimeout(()=>{
          this.router.navigateByUrl('home/admin')
        },1000)
      }     
    })
  }
}

