import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../../_service/auth/auth.service';

export const notAuthGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const router= inject(Router)
  const authService= inject(AuthService)

  if(!authService.isAuthenticated())
    return true
  else{
    const role = authService.getRole()
    if(role=="ROLE_ADMIN")
      router.navigateByUrl("home/admin")
    else if(role=="ROLE_AGENT")
      router.navigateByUrl("home/agent")
    else if(role=="ROLE_CUSTOMER")
      router.navigateByUrl("home/customer")
    return false 
  }

};
