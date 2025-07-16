import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginService } from '../../rest-backend/login/login.service';
import { AuthService } from '../../_service/auth/auth.service';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment.prod';

declare const google: any;

@Component({
    selector: 'app-login-with-google',
    imports: [
      CommonModule
    ],
    templateUrl: './login-with-google.component.html',
    styleUrls: ['./login-with-google.component.scss']
})
export class LoginWithGoogleComponent implements OnInit  {
  loggedIn: boolean|undefined
  user: string|undefined
 
  constructor(
    private readonly loginService:LoginService,
    private readonly authService: AuthService, 
    private readonly router:Router
  ){}

  
  ngOnInit(): void {
    if (typeof google !== 'undefined') {
      google.accounts.id.initialize({
        client_id: environment.googleApiKey,
        callback: this.handleCredentialResponse.bind(this)
      });
    }
  }

  ngAfterViewInit(): void {
    if (typeof google !== 'undefined') {
      google.accounts.id.renderButton(
        document.getElementById('google-signin-button'),
        { theme: 'outline', size: 'medium' }
      );
    }
  }

  handleCredentialResponse(response: any): void {
    console.log('Encoded JWT ID token: ');
    console.log(response)
    this.loginService.loginWithGoogle(response.credential).subscribe({
      error: (err)=>{
        console.log(err)
      },
      next: (res)=>{
        console.log(res)
        this.authService.updateToken(res.jwtToken)
        setTimeout(()=>
          this.router.navigateByUrl("home/customer")  
        )
      }
    })
  }

}