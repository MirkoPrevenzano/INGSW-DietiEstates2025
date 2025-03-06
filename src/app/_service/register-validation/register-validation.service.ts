import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class RegisterValidationService {
  isInvalidForm(registerForm: FormGroup): boolean {
    return registerForm.invalid ||
           registerForm.value.name === '' ||
           registerForm.value.lastname === '' ||
           registerForm.value.email === '' ||
           registerForm.value.password === '';
  }

  isEmailInvalid(email: string): boolean {
    const emailPattern = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,63}$/;
    return !emailPattern.test(email);
  }

 
}