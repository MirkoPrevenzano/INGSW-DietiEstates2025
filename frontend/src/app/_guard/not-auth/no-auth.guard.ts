import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../../_service/auth/auth.service';
import { RedirectHomeService } from '../../_service/redirect-home/redirect-home.service';

export const notAuthGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const router= inject(Router)
  const authService= inject(AuthService)
  const redirectService = inject(RedirectHomeService)

  if(!authService.isAuthenticated())
    return true
  else{
    const defaultHome = redirectService.determineDefaultHome();
    router.navigate([defaultHome]);
    return false; 
  }

};
