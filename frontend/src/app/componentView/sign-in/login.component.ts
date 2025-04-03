import { Component } from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { LoginService } from '../../_service/rest-backend/login/login.service';
import { CommonModule } from '@angular/common';
import { LoginWithGoogleComponent } from './login-with-google/login-with-google.component';
import { AuthService } from '../../_service/auth/auth.service';
import { FormFieldComponent } from '../form-field/form-field.component';
import { PasswordFieldComponent } from '../password-field/password-field.component';
import {  IndividualConfig, ToastrService } from 'ngx-toastr';
import { Router, RouterModule } from '@angular/router';
import { ButtonCustomComponent } from '../button-custom/button-custom.component';

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
    
  ) {}
  loginForm =new FormGroup({
    userType: new FormControl('customer'),
    username: new FormControl(''),
    password: new FormControl('')
  });

  signIn() {
    if (this.isInvalidForm()) {
      const toastrConfig: Partial<IndividualConfig> = {
        positionClass: 'toast-top-center',
        timeOut: 1000,
      };
      this.notifyToastr.warning("Please fill out all fields before submitting.", "Form Incomplete", toastrConfig);
    }
    else if (this.isValidUsername()) {
      this.authenticateUser();
    } else {
      this.notifyToastr.warning("Please enter a valid email address.", "Invalid Email Format");
    }
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
        console.log(err);
      }
    });
  }


  redirectHomePage() {
    const role= this.authService.getRole()
    console.log(role)
    if(role=="ROLE_ADMIN" || role=="ROLE_COLLABORATOR")
      this.router.navigateByUrl("home/admin")
    else if(role=="ROLE_AGENT")
      this.router.navigateByUrl("home/agent")
    else if(role=="ROLE_CUSTOMER")
      this.router.navigateByUrl("home/customer")
    else if(role=="ROLE_UNAUTHORIZED"){
      console.log("CIao")

      this.router.navigateByUrl("admin/change-password")
    }

    this.notifyToastr.success(`Welcome ${this.loginForm.value.username}`)
  }
  goGithub() {
    this.loginService.loginGit().subscribe((token)=>{
      console.log(token)
    })
  }
    
  



}
 

