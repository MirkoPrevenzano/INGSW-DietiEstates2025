import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { RegisterService } from '../../_service/rest-backend/register/register.service';
import { RegisterValidationService } from '../../_service/register-validation/register-validation.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  constructor(
    private readonly register:RegisterService,
    private readonly registerValidation:RegisterValidationService
  ){ }

  registerForm= new FormGroup({
    name: new FormControl(''),
    lastname: new FormControl(''),
    email: new FormControl(''),
    password: new FormControl('')
  })

  onRegister()
  {
    if(this.registerValidation.isInvalidForm(this.registerForm)){
      console.log("Complile all field")
    }
    else if(this.registerValidation.isEmailInvalid(this.registerForm.value.email!))
    {
      console.log("email form not valid")
    }
    else 
    {
      const errorMessage=this.registerValidation.validatePassword(this.registerForm.value.password!)
      if(errorMessage.length>0)
        console.log("Password not secure")
      else{
        this.register.registrate({
          name: this.registerForm.value.name as string,
          surname: this.registerForm.value.lastname as string,
          username: this.registerForm.value.email as string,
          password: this.registerForm.value.password as string
        }).subscribe({
          error: (err)=>{
            console.log("Error register:"+err)
          },
          next: (res)=>{
            console.log(res)
          }
        })
      }
    }
   
      
  }
}
