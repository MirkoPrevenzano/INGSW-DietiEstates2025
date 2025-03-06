import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../../_service/auth/auth.service';

export const authGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const router= inject(Router)
  const authService= inject(AuthService)

  if(authService.isAuthenticated())
    return true
  router.navigate(['/login'])

  return false
};
