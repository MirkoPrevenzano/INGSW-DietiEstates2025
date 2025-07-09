import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RedirectHomeService {

  constructor(private readonly authService: AuthService) {}

  determineDefaultHome(): string {
    const userRole = this.authService.getRole(); 
    switch (userRole) {
      case 'ROLE_CUSTOMER':
        return '/home/customer';
      case 'ROLE_AGENT':
        return '/home/agent';
      case 'ROLE_ADMIN':
      case 'ROLE_COLLABORATOR':
        return '/home/admin';
      case 'ROLE_UNAUTHORIZED':
        return '/admin/change-password';
      default:
        return '/home';
    }
  }
}
