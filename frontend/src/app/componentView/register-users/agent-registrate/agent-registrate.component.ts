import { Component, inject } from '@angular/core';
import { RegisterComponent } from '../register/register.component';
import { SavePersonalService } from '../../../_service/rest-backend/save-personal/save-personalservice';
import { ReactiveFormsModule } from '@angular/forms';
import { RegisterRequest } from '../../../model/registerRequest';
import { FormFieldComponent } from '../../form-field/form-field.component';
import { CommonModule } from '@angular/common';
import { PasswordFieldComponent } from '../../password-field/password-field.component';
import { ignoreElements } from 'rxjs';
import { Router } from '@angular/router';
import { ButtonCustomComponent } from '../../button-custom/button-custom.component';
import { LoginWithGoogleComponent } from '../../login-with-google/login-with-google.component';

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
  private readonly savePersonalService = inject(SavePersonalService)
  private readonly router = inject(Router)
  override title: string="Create new agent";
  protected override onRegisterUser(userRequest: RegisterRequest): void {
    this.savePersonalService.saveAgent(userRequest).subscribe({
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

