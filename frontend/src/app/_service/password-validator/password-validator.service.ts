import {Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PasswordValidatorService {

  constructor() { }
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
