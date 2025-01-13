import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { SaveAgentService } from '../../_service/rest-backend/save-agent/save-agent.service';
import { RegisterValidationService } from '../../_service/register-validation/register-validation.service';

@Component({
  selector: 'app-registrate-agent',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './registrate-agent.component.html',
  styleUrl: './registrate-agent.component.scss'
})
export class RegistrateAgentComponent {
constructor(
  private readonly saveAgentService: SaveAgentService,
  private readonly registerValidator: RegisterValidationService
){}

  agentSaveForm = new FormGroup({
    username : new FormControl(''),
    password: new FormControl('')
  });

  onSaveAgent(){
    const error:string=this.validateCommit()
    if(error.length==0){
      this.saveAgentService.saveAgent(
        {
          username: this.agentSaveForm.value.username as string,
          password: this.agentSaveForm.value.password as string
        }
      ).subscribe({
        error: (err)=>{
          console.log(err)
        },
        complete:()=> {
          console.log("agent add with success")
        }     
      })
    }
    else 
      console.log(error)
  }

  validateCommit():string{
    let error= ''
    if(this.registerValidator.isInvalidForm(this.agentSaveForm)){
      error='Compile all field'
    }
    else
      error=this.registerValidator.validatePassword(this.agentSaveForm.value.password as string).join(', ')
    return error
  }
}


