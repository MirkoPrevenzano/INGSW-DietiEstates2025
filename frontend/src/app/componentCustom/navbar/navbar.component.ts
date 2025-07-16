import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive} from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../_service/auth/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'navbar-component',
    imports: [RouterLink, RouterLinkActive, CommonModule],
    templateUrl: './navbar.component.html',
    styleUrl: './navbar.component.scss'
})
export class NavbarComponent {
  isMenuOpen = false;
  isUserMenuOpen = false;
  user = localStorage.getItem('user')
  role = localStorage.getItem('role')
  authService = inject(AuthService);
  router = inject(Router)
  toastrService = inject(ToastrService)

  

  toggleUserMenu() {  this.isUserMenuOpen = !this.isUserMenuOpen; }


  toggleMenu() {  this.isMenuOpen = !this.isMenuOpen; }

  signOut(){
    this.authService.logout()    
    setTimeout(() => {
      this.router.navigateByUrl('/home').then(() => {
        this.toastrService.success("You have successfully signed out. See you soon!", "Goodbye!", {
          timeOut: 3000,
          positionClass: 'toast-top-center',
          closeButton: true
        })
      })
    }, 100); // Ritarda la navigazione di 2 secondi

  }

  goHome(){
    const role = localStorage.getItem("role")
    if(role==="ROLE_CUSTOMER")
      this.router.navigateByUrl("/home/customer")
    else if(role === "ROLE_ADMIN")
      this.router.navigateByUrl("/home/admin")
    else if(role == "ROLE_AGENT")
      this.router.navigateByUrl("/home/agent")
    else
      this.router.navigateByUrl("/home")
  }

  
  
}
