import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { LoginService } from '../../rest-backend/login/login.service';
import { CommonModule } from '@angular/common';
import { LoginWithGoogleComponent } from '../../componentCustom/login-with-google/login-with-google.component';
import { AuthService } from '../../_service/auth/auth.service';
import { FormFieldComponent } from '../../componentCustom/form-field/form-field.component';
import { PasswordFieldComponent } from '../../componentCustom/password-field/password-field.component';
import {  IndividualConfig, ToastrService } from 'ngx-toastr';
import { Router, RouterModule } from '@angular/router';
import { ButtonCustomComponent } from '../../componentCustom/button-custom/button-custom.component';
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
export class LoginComponent implements OnInit{
  password = ''
  userType = ''
  username = ''

  constructor(
    private readonly loginService: LoginService,
    private readonly authService: AuthService,
    private readonly notifyToastr: ToastrService,
    private readonly router: Router,
    private readonly redirectHomeService: RedirectHomeService,
    private readonly passwordValidator: PasswordValidatorService
    
  ) {}
  loginForm =new FormGroup({
    userType: new FormControl('Customer'),
    username: new FormControl(''),
    password: new FormControl('')
  });

  ngOnInit(): void {
    this.loginForm.valueChanges.subscribe(values => {
      this.password = values.password ?? ''
      this.userType = values.userType ?? ''
      this.username = values.username ?? ''
    });
  }

  

  signIn() {
    if (this.isInvalidForm()) {
      const toastrConfig: Partial<IndividualConfig> = {
        timeOut: 2000,
      };
      this.notifyToastr.warning("Please fill out all fields before submitting.", "Form Incomplete", toastrConfig);
    }
    else if (this.isValidField()) {
      this.authenticateUser()
    }
    
  }

  isValidField() {
    let isValid = true
    if(!this.isValidUsername())
    {
      this.notifyToastr.warning("Please enter a valid email address.", "Invalid Email Format");
      isValid=false
    }
    let errorPassword = this.passwordValidator.validatePassword(this.password)
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
           !this.username ||
           !this.password
  }

  isValidUsername() {
    if (this.loginForm.value.userType === 'Customer') {
      const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
      return emailPattern.test(this.username)
    }
    return true;
  }

  authenticateUser() {
    this.loginService.login({
      username: this.username,
      password: this.password,
      role: this.userType
    }).subscribe({
      next: (response) => {
  
        const token = response.jwtToken;
        console.log('Token:', token);
  
        if (token) {
          this.authService.updateToken(token);
          setTimeout(() => {
            this.redirectHomePage(); 
          }, 0);
        } else {
          this.notifyToastr.warning('Token non trovato nella risposta del server.');
        }
      },
      error: (err) => {
        console.log(err)
        if(err?.error.status >= 400 && err?.error.status < 500)
          this.notifyToastr.warning(err?.error.detail)
        if(err?.error.status >= 500 && err?.error.status < 600)
          this.notifyToastr.error(err?.error.detail)
      }
    });
  }


  redirectHomePage() {
    const path = this.redirectHomeService.determineDefaultHome()
    this.router.navigateByUrl(path)
    this.notifyToastr.success(`Welcome ${this.username}`)
    
  }
 
    
  



}
 

