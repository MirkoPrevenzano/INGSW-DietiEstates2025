import { Component, Inject, inject } from '@angular/core';
import { RegisterComponent } from '../register/register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { FormFieldComponent } from '../../../componentCustom/form-field/form-field.component';
import { CommonModule } from '@angular/common';
import { PasswordFieldComponent } from '../../../componentCustom/password-field/password-field.component';
import { Router } from '@angular/router';
import { ButtonCustomComponent } from '../../../componentCustom/button-custom/button-custom.component';
import { LoginWithGoogleComponent } from '../../../componentCustom/login-with-google/login-with-google.component';
import { AgentCreation } from '../../../model/request/agentCreation';
import { HandleNotifyService } from '../../../_service/handle-notify.service';
import { AgentService } from '../../../rest-backend/agent/agent.service';

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
  private readonly agentService = inject(AgentService)
  private readonly router = inject(Router)
  private readonly handleError = inject(HandleNotifyService)
  override title: string="Create new agent";

  protected override onRegisterUser(userRequest: AgentCreation): void {
    this.agentService.saveAgent(userRequest).subscribe({
      error: (err) => {
        this.handleError.showMessageError(err.error)
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
      const userRequest: AgentCreation ={
        name:this.registerForm.value.name,
        surname: this.registerForm.value.lastname,
        username: this.registerForm.value.username,
      }
      this.onRegisterUser(userRequest)
    }    
  }
}

