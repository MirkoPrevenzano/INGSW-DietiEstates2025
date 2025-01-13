import { Component, inject } from '@angular/core';
import { RouterLink, RouterLinkActive} from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../_service/auth/auth.service';

@Component({
  selector: 'navbar-component',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {
  isMenuOpen = false;
  isUserMenuOpen = false;

  authService = inject(AuthService);


  toggleUserMenu() {  this.isUserMenuOpen = !this.isUserMenuOpen; }


  toggleMenu() {  this.isMenuOpen = !this.isMenuOpen; }


  
  
}
