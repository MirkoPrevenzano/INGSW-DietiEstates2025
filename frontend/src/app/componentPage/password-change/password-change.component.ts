import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators,  } from '@angular/forms';
import { PasswordFieldComponent } from '../../componentCustom/password-field/password-field.component';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { PasswordChangeControlService } from '../../_service/password-change/password-change-control.service';
import { ButtonCustomComponent } from '../../componentCustom/button-custom/button-custom.component';
import { AuthService } from '../../_service/auth/auth.service';
import { LoginService } from '../../rest-backend/login/login.service';
import { Authentication } from '../../model/response/authentication';
import { HandleNotifyService } from '../../_service/handle-notify.service';
import { UserService } from '../../rest-backend/user/user.service';

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
      private readonly userService:UserService,
      private readonly loginService:LoginService,
      private readonly passwordChangeControlService: PasswordChangeControlService,
      private readonly notify: ToastrService,
      private readonly router: Router,
      private readonly authService:AuthService,
      private readonly handleError:HandleNotifyService
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
      this.userService.updatePassword({
        oldPassword: this.oldPassword,
        newPassword: this.newPassword
      }).subscribe({
        error: (err) => {
          this.handleError.showMessageError(err.error)
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
      next: (response:Authentication) => {
        const token = response.jwtToken;
        this.authService.updateToken(token);
        setTimeout(() => {
          this.router.navigateByUrl('home/admin');
        }, 0);
      },
      error: (err) => {
        this.handleError.showMessageError(err.error)
      }
    });
  }

  isMatch(): boolean {
    return this.passwordChangeControlService.isMatch(
      this.newPassword ,
      this.confirmPassword
    );
  }


   

    

  
}


