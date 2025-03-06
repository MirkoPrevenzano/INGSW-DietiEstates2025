import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RegisterValidationService } from '../../../_service/register-validation/register-validation.service';
import { RegisterRequest } from '../../../model/registerRequest';
import { CommonModule } from '@angular/common';
import { FormFieldComponent } from '../../form-field/form-field.component';
import { PasswordValidatorService } from '../../../_service/password-validator/password-validator.service';
import { PasswordFieldComponent } from '../../password-field/password-field.component';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, RouterLink, RouterModule } from '@angular/router';

@Component({
    selector: 'app-register',
    imports: [
      ReactiveFormsModule, 
      CommonModule,
      FormFieldComponent,
      PasswordFieldComponent,
      RouterModule
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
  
  constructor(
    protected readonly registerValidation:RegisterValidationService,
    protected readonly notify: ToastrService,
    
  ){ 
    this.registerForm = new FormGroup({
      name: new FormControl('', Validators.required),
      lastname: new FormControl('', Validators.required),
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }

  ngOnInit(): void {
    this.route.url.subscribe(url => {
      this.isCustomer = url.some(segment => segment.path === 'register');
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
    else{
      const userRequest: RegisterRequest ={
        name:this.registerForm.value.name,
        surname: this.registerForm.value.lastname,
        username: this.registerForm.value.username,
        password: this.registerForm.value.password
      }
      console.log(userRequest.password)
      this.onRegisterUser(userRequest)
    }   
  }

  protected onRegisterUser(userRequest: RegisterRequest){
    //ereditato
  }

  
}
