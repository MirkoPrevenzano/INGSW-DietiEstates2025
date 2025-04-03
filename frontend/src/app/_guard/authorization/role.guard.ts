import { inject } from '@angular/core';
import {  ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../../_service/auth/auth.service';
import { RedirectHomeService } from '../../_service/redirect-home/redirect-home.service';

export const roleGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state:RouterStateSnapshot) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const redirectService = inject(RedirectHomeService)

  const expectedRole = route.data['expectedRole'];
  const role = authService.getRole();
  if (authService.isAuthenticated() && expectedRole.includes(role)) {
    return true;
  } else {
    const defaultHome = redirectService.determineDefaultHome();
    router.navigate([defaultHome]);
    return false;
  }
  
};
