import { inject } from '@angular/core';
import {  ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../../_service/auth/auth.service';

export const roleGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state:RouterStateSnapshot) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const expectedRole = route.data['expectedRole'];
  const role = authService.getRole();
  if (authService.isAuthenticated() && expectedRole.includes(role)) {
    return true;
  } else {

    if(role=="ROLE_ADMIN" || role=="ROLE_COLLABORATOR")
      router.navigateByUrl("home/admin")
    else if(role=="ROLE_AGENT")
      router.navigateByUrl("home/agent")
    else if(role=="ROLE_CUSTOMER")
      router.navigateByUrl("home/customer")
    else if(role=="ROLE_UNAUTHORIZED")
      router.navigateByUrl("admin/change-password")

    
    return false 
  }
  
};
