import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { PasswordValidatorService } from '../password-validator/password-validator.service';

@Injectable({
  providedIn: 'root'
})
export class PasswordChangeControlService {

  constructor(
    private readonly notify: ToastrService,
    private readonly passwordValidatorService: PasswordValidatorService
  ) { }

  isValidNewPassword(
    newPassword:string,
    oldPassword:string,
    confirmNewPassword:string
  ) {
    if(newPassword === '' || oldPassword === '' || confirmNewPassword === '') {
      this.notify.warning('All fields are required');
    } else if(!this.isMatch(newPassword, confirmNewPassword))
      this.notify.warning('Password should be match')
    else if(this.isEqualNewOldPassword(newPassword, oldPassword))
      this.notify.warning("The 'new password' you have inserted can't be equal to your current password")
    else {
      const errorMessage= this.passwordValidatorService.validatePassword(newPassword);
      if(errorMessage.length == 0 )
        return true
      else{
        const formattedErrorMessage = errorMessage.map(msg => `<b>=></b>${msg}`).join('<br>'); // Avvolgi ogni elemento con <li> e unisci
        this.notify.warning('<center><b>Password not secure</b></center><br> ' + formattedErrorMessage+'', '', {
          enableHtml: true // Abilita l'HTML nel messaggio di avviso
        });
      }
    }

    return false
  }

  isMatch(newPassword: string, confirmNewPassword: string){
    return newPassword === confirmNewPassword;
  }

  isEqualNewOldPassword(newPassword: string, oldPassword: string) {
    return newPassword === oldPassword
  }
}
