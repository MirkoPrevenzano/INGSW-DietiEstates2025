import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule,  } from '@angular/forms';
import { PasswordChangeService } from '../../_service/rest-backend/password-change/password-change.service';

@Component({
  selector: 'app-password-change',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './password-change.component.html',
  styleUrl: './password-change.component.scss'
})
export class PasswordChangeComponent {
    constructor(
      private readonly passwordService:PasswordChangeService
    ){}

    passwordForm= new FormGroup({
      oldPassword : new FormControl(''),
      newPassword : new FormControl(''),
      confirmNewPassword : new FormControl('')
    });

    onChangePassword(){
      if(this.isMatch())
      {
        if(this.isValidNewPassword()){
          this.passwordService.passwordChange({
            oldPassword: this.passwordForm.value.oldPassword as string,
            newPassword: this.passwordForm.value.newPassword as string,
            username: localStorage.getItem('username') as string
          }).subscribe({
            error: (err)=>{
              console.log(err)
            },
            complete: ()=>{
              console.log("success change password")
            }

          })
        }
      }
    }


    isMatch(){
      const newPassword= this.passwordForm.value.newPassword;
      const confirmPassword= this.passwordForm.value.confirmNewPassword
      return newPassword === confirmPassword;
    }

    isValidNewPassword() {
      return true;
    }
}


