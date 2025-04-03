import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { RedirectHomeService } from '../../_service/redirect-home/redirect-home.service';

export const RedirectGuard: CanActivateFn = (route, state) => {
  const router = inject(Router)
  const redirectService = inject(RedirectHomeService)
  const defaultHome = redirectService.determineDefaultHome()
  console.log(defaultHome)
  router.navigate([defaultHome])
  return false
};
