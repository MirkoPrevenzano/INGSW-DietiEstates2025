import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators,  } from '@angular/forms';
import { PasswordChangeService } from '../../_service/rest-backend/password-change/password-change.service';
import { PasswordFieldComponent } from '../password-field/password-field.component';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { PasswordChangeControlService } from '../../_service/password-change/password-change-control.service';
import { ButtonCustomComponent } from '../button-custom/button-custom.component';

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
      private readonly passwordChangeControlService: PasswordChangeControlService,
      private readonly notify: ToastrService,
      private readonly router: Router
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
          this.notify.error(err);
        },
        complete: () => {
          this.notify.success('Success change password');
          setTimeout(() => {
            this.router.navigateByUrl('home/admin');
          }, 1000);
        }
      })
  }

  isMatch(): boolean {
    return this.passwordChangeControlService.isMatch(
      this.passwordForm.value.newPassword!,
      this.passwordForm.value.confirmNewPassword!
    );
  }


   

    

  
}


