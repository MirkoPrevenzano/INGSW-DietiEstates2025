import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
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
export class PasswordChangeComponent implements OnInit{
    constructor(
      private readonly passwordService:PasswordChangeService,
      private readonly loginService:LoginService,
      private readonly passwordChangeControlService: PasswordChangeControlService,
      private readonly notify: ToastrService,
      private readonly router: Router,
      private readonly authService:AuthService
    ){}

    ngOnInit(): void {
      this.passwordForm.valueChanges.subscribe(values => {
        this.oldPassword = values.oldPassword ?? '';
        this.newPassword = values.newPassword ?? '';
        this.confirmPassword = values.confirmNewPassword ?? '';
      });
    }

    passwordForm = new FormGroup({
      oldPassword: new FormControl('', [Validators.required]),
      newPassword: new FormControl('', [Validators.required, Validators.minLength(8)]),
      confirmNewPassword: new FormControl('', [Validators.required])
    });

    private oldPassword = ''
    private newPassword = ''
    private confirmPassword = ''
 

    submitPasswordChange(){
     
      if (this.passwordChangeControlService.isValidNewPassword(
        this.newPassword,
        this.oldPassword,
        this.confirmPassword
      )) {
        this.executePasswordChange()
      }
    }


    executePasswordChange() {
      this.passwordService.passwordChange({
        oldPassword: this.oldPassword,
        newPassword: this.newPassword
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

  //effettua il logout e il login con la nuova password per l'admin che ha la password di default
  //in modo da poter accedere con la nuova password
  reloadLogin() {
    const user = this.authService.getUser()
    if(!user){
      throw new Error("Error user")
    }
    this.loginService.login({
      username: user,
      password: this.newPassword,
      role: "admin"
    }).subscribe({
      next: (response) => {
        const token = response.jwtToken;
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
      this.oldPassword ,
      this.confirmPassword
    );
  }


   

    

  
}


