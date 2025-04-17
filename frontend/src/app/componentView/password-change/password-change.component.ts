import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators,  } from '@angular/forms';
import { PasswordChangeService } from '../../_service/rest-backend/password-change/password-change.service';
import { PasswordFieldComponent } from '../password-field/password-field.component';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { PasswordChangeControlService } from '../../_service/password-change/password-change-control.service';
import { ButtonCustomComponent } from '../button-custom/button-custom.component';
import { AuthService } from '../../_service/auth/auth.service';
import { LoginService } from '../../_service/rest-backend/login/login.service';

@Component({
    selector: 'app-password-change',
    imports: [
      ReactiveFormsModule, 
      CommonModule, 
      PasswordFieldComponent,
      ButtonCustomComponent
    ],
    templateUrl: './password-change.component.html',
    styleUrl: './password-change.component.scss'
})
export class PasswordChangeComponent {
    constructor(
      private readonly passwordService:PasswordChangeService,
      private readonly loginService:LoginService,
      private readonly passwordChangeControlService: PasswordChangeControlService,
      private readonly notify: ToastrService,
      private readonly router: Router,
      private readonly authService:AuthService
    ){}

    passwordForm = new FormGroup({
      oldPassword: new FormControl('', [Validators.required]),
      newPassword: new FormControl('', [Validators.required, Validators.minLength(8)]),
      confirmNewPassword: new FormControl('', [Validators.required])
    });

    onChangePassword(){
     
      if (this.passwordChangeControlService.isValidNewPassword(
        this.passwordForm.value.newPassword!,
        this.passwordForm.value.oldPassword!,
        this.passwordForm.value.confirmNewPassword!
      )) {
        this.passwordChange()
      }
    }


    passwordChange() {
      this.passwordService.passwordChange({
        oldPassword: this.passwordForm.value.oldPassword as string,
        newPassword: this.passwordForm.value.newPassword as string,
      }).subscribe({
        error: (err) => {
          this.notify.warning(err.headers.get('error'))
        },
        complete: () => {
          this.notify.success('Success change password');
          if(this.passwordForm.value.oldPassword=='default'){
            this.reloadLogin()
          }else{
            setTimeout(() => {
              this.router.navigateByUrl('home/admin');
            }, 1000);
          }
          
        }
      })
  }
  reloadLogin() {
    this.loginService.login({
      username: this.authService.getUser()!,
      password: this.passwordForm.value.newPassword as string,
      role: "admin"
    }).subscribe({
      next: (response) => {
        const token = response.accessToken;
        this.authService.updateToken(token);
        setTimeout(() => {
          this.router.navigateByUrl('home/admin');
        }, 0);
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  isMatch(): boolean {
    return this.passwordChangeControlService.isMatch(
      this.passwordForm.value.newPassword!,
      this.passwordForm.value.confirmNewPassword!
    );
  }


   

    

  
}


