import { Component } from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { LoginService } from '../../_service/rest-backend/login/login.service';
import { CommonModule } from '@angular/common';
import { LoginWithGoogleComponent } from '../login-with-google/login-with-google.component';
import { AuthService } from '../../_service/auth/auth.service';
import { FormFieldComponent } from '../form-field/form-field.component';
import { PasswordFieldComponent } from '../password-field/password-field.component';
import {  IndividualConfig, ToastrService } from 'ngx-toastr';
import { Router, RouterModule } from '@angular/router';
import { ButtonCustomComponent } from '../button-custom/button-custom.component';
import { RedirectHomeService } from '../../_service/redirect-home/redirect-home.service';
import { PasswordValidatorService } from '../../_service/password-validator/password-validator.service';

@Component({
    selector: 'app-login-customer',
    imports: [
      ReactiveFormsModule, 
      LoginWithGoogleComponent, 
      CommonModule,
      FormFieldComponent,
      PasswordFieldComponent,
      RouterModule,
      ButtonCustomComponent
    ],
    templateUrl: './login.component.html',
    styleUrl: './login.component.scss'
})
export class LoginComponent {

  constructor(
    private readonly loginService: LoginService,
    private readonly authService: AuthService,
    private readonly notifyToastr: ToastrService,
    private readonly router: Router,
    private readonly redirectHomeService: RedirectHomeService,
    private readonly passwordValidator: PasswordValidatorService
    
  ) {}
  loginForm =new FormGroup({
    userType: new FormControl('customer'),
    username: new FormControl(''),
    password: new FormControl('')
  });

  signIn() {
    if (this.isInvalidForm()) {
      const toastrConfig: Partial<IndividualConfig> = {
        timeOut: 2000,
      };
      this.notifyToastr.warning("Please fill out all fields before submitting.", "Form Incomplete", toastrConfig);
    }
    else if (this.isValidField()) {
      this.authenticateUser();
    }
    
  }

  isValidField() {
    let isValid = true
    if(!this.isValidUsername())
    {
      this.notifyToastr.warning("Please enter a valid email address.", "Invalid Email Format");
      isValid=false
    }
    let errorPassword = this.passwordValidator.validatePassword(this.loginForm.value.password ?? '')
    if(errorPassword.length>0){
      const formattedErrorMessage = errorPassword.map(msg => `<b>=></b>${msg}`).join('<br>'); // Avvolgi ogni elemento con <li> e unisci
      this.notifyToastr.warning('<center><b>Password not secure</b></center><br> ' + formattedErrorMessage+'', '', {
        enableHtml: true // Abilita l'HTML nel messaggio di avviso
      });
      isValid = false
    }
    return isValid
  }

  isInvalidForm() {
    return this.loginForm.invalid ||
           this.loginForm.value.username === '' ||
           this.loginForm.value.password === '';
  }

  isValidUsername() {
    if (this.loginForm.value.userType === 'customer') {
      const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
      return emailPattern.test(this.loginForm.value.username as string);
    }
    return true;
  }

  authenticateUser() {
    this.loginService.login({
      username: this.loginForm.value.username as string,
      password: this.loginForm.value.password as string,
      role: this.loginForm.value.userType as string
    }).subscribe({
      next: (response) => {
        const token = response.accessToken;
        this.authService.updateToken(token);
        setTimeout(()=>{
          this.redirectHomePage()
        },0)
      },
      error: (err) => {
        this.notifyToastr.warning(err.headers.get('error'))
      }
    });
  }


  redirectHomePage() {
    const path = this.redirectHomeService.determineDefaultHome(); // Chiamata al servizio
    this.router.navigateByUrl(path);
    this.notifyToastr.success(`Welcome ${this.loginForm.value.username}`);
    
  }
 
    
  



}
 

