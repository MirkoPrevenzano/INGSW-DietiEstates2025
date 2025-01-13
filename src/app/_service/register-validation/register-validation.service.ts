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
    const emailPattern = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
    return !emailPattern.test(email);
  }

  validatePassword(password: string): string[] {
    const errors: string[] = [];
    if (password.length < 10) {
      errors.push('Password must be at least 10 characters long.');
    }
    if (!/[A-Z]/.test(password)) {
      errors.push('Password must contain at least one uppercase letter.');
    }
    if (!/\d/.test(password)) {/**\d presenta qualsiasi cifra decimale */
      errors.push('Password must contain at least one digit.');
    }
    if (!/[!@#$%^&*()_+[\]{}|;:,.<>?]/.test(password)) {
      errors.push('Password must contain at least one special character.');
    }
    return errors;
  }
}