import { Component } from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { LoginService } from '../../_service/rest-backend/login/login.service';
import { CommonModule } from '@angular/common';
import { LoginWithGoogleComponent } from './login-with-google/login-with-google.component';
import { AuthService } from '../../_service/auth/auth.service';

@Component({
  selector: 'app-login-customer',
  standalone: true,
  imports: [ReactiveFormsModule, LoginWithGoogleComponent,CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  constructor(
    private readonly loginService: LoginService,
    private readonly authService: AuthService
  ) {}
  loginForm =new FormGroup({
    userType: new FormControl('customer'),
    username: new FormControl(''),
    password: new FormControl('')
  });

  signIn() {
    if (this.isInvalidForm()) {
      console.log('Input non valido');
    } else if (this.isValidUsername()) {
      this.authenticateUser();
    } else {
      console.log('Username non valido');
    }
  }

  isInvalidForm() {
    return this.loginForm.invalid ||
           this.loginForm.value.username === '' ||
           this.loginForm.value.password === '';
  }

  isValidUsername() {
    if (this.loginForm.value.userType === 'Customer') {
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
      next: (response: { token: string; username: string }) => {
        const token = response.token;
        this.authService.updateToken(token);
      },
      error: (err) => {
        console.log(err);
      }
    });
  }
  



}
 

