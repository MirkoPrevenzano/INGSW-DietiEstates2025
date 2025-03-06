import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginService } from '../../../_service/rest-backend/login/login.service';

declare const google: any;

@Component({
    selector: 'app-login-with-google',
    imports: [CommonModule],
    templateUrl: './login-with-google.component.html',
    styleUrls: ['./login-with-google.component.scss']
})
export class LoginWithGoogleComponent implements OnInit  {
  loggedIn: boolean|undefined
  user: string|undefined
 
  constructor(
    private readonly loginService:LoginService,
  ){}

  
  ngOnInit(): void {
    // Ensure google object is available
    if (typeof google !== 'undefined') {
      google.accounts.id.initialize({
        client_id: '699354462746-9ale2lg8onjqvafu9aiopmd0fo82j3b4.apps.googleusercontent.com',
        callback: this.handleCredentialResponse.bind(this)
      });
    }
  }

  ngAfterViewInit(): void {
    // Ensure google object is available
    if (typeof google !== 'undefined') {
      google.accounts.id.renderButton(
        document.getElementById('google-signin-button'),
        { theme: 'outline', size: 'large' }
      );
    }
  }

  handleCredentialResponse(response: any): void {
    console.log('Encoded JWT ID token: ' + response);
    this.loginService.loginWithGoogle(response.credential).subscribe({
      error: (err)=>{
        console.log(err)
      },
      next: (res)=>{
        console.log(res)
      }
    })
    // Handle the response and authenticate the user
  }

 
  /*ngOnInit() {
    this.initGoogleAuth();
  }

  initGoogleAuth() {
    loadGapiInsideDOM().then(() => {
      gapi.load('auth2', () => {
        gapi.auth2.init({
          client_id:  '699354462746-9ale2lg8onjqvafu9aiopmd0fo82j3b4.apps.googleusercontent.com'
        });
      });
    });
  }

  login() {
    const auth2 = gapi.auth2.getAuthInstance();
    auth2.signIn().then((googleUser: any) => {
      const idToken = googleUser.getAuthResponse().id_token;
      this.loginService.loginWithGoogle(idToken).subscribe(response => {
        console.log('User authenticated:', response);
      });
    });
  }*/
}