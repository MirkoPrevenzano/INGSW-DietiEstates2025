import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RegisterValidationService } from '../../../_service/register-validation/register-validation.service';
import { CommonModule } from '@angular/common';
import { FormFieldComponent } from '../../../componentCustom/form-field/form-field.component';
import { PasswordValidatorService } from '../../../_service/password-validator/password-validator.service';
import { PasswordFieldComponent } from '../../../componentCustom/password-field/password-field.component';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ButtonCustomComponent } from '../../../componentCustom/button-custom/button-custom.component';
import { LoginWithGoogleComponent } from '../../../componentCustom/login-with-google/login-with-google.component';
import { CustomerRegistration } from '../../../model/request/customerRegistration';

@Component({
    selector: 'app-register',
    imports: [
      ReactiveFormsModule, 
      CommonModule,
      FormFieldComponent,
      PasswordFieldComponent,
      RouterModule,
      ButtonCustomComponent,
      LoginWithGoogleComponent
    ],
    templateUrl: './register.component.html',
    styleUrl: './register.component.scss'
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup
  private readonly passwordValidator: PasswordValidatorService = inject(PasswordValidatorService)
  private readonly route: ActivatedRoute = inject(ActivatedRoute)
  title: string =""
  isCustomer: boolean = false
  isCreateCollaborator:boolean = false
  isCreateAgent: boolean = false
  
  constructor(
    protected readonly registerValidation:RegisterValidationService,
    protected readonly notify: ToastrService,
    
  ){ 
    this.registerForm = new FormGroup({
      name: new FormControl('', Validators.required),
      lastname: new FormControl('', Validators.required),
      username: new FormControl('', Validators.required),
      password: new FormControl('')
    });
  }

  ngOnInit(): void {
    this.route.url.subscribe(url => {
      this.isCustomer = url.some(segment => segment.path === 'register');
      this.isCreateCollaborator = url.some(segment => segment.path === 'create-collaborator')
      this.isCreateAgent = url.some(segment => segment.path == 'create-admin')
    });
  }

  onRegister()
  {
    
    const errorMessage=this.passwordValidator.validatePassword(this.registerForm.value.password)
    if(this.registerValidation.isInvalidForm(this.registerForm)){
      this.notify.warning('Compile all field')
    }
    else if(errorMessage.length>0){
        const formattedErrorMessage = errorMessage.map(msg => `<b>=></b>${msg}`).join('<br>'); // Avvolgi ogni elemento con <li> e unisci
        this.notify.warning('<center><b>Password not secure</b></center><br> ' + formattedErrorMessage+'', '', {
          enableHtml: true // Abilita l'HTML nel messaggio di avviso
        });
    }
    else if(this.registerValidation.isEmailInvalid(this.registerForm.value.username)){
      this.notify.warning('Email is not valid')
    }
    else{
      const userRequest: CustomerRegistration ={
        name:this.registerForm.value.name,
        surname: this.registerForm.value.lastname,
        username: this.registerForm.value.username,
        password: this.registerForm.value.password
      }
      console.log(userRequest.password)
      this.onRegisterUser(userRequest)
    }   
  }

  protected onRegisterUser(userRequest: CustomerRegistration){
    //ereditato
  }

  
}
