import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Identity } from '../../identity/identity';
import { map } from 'rxjs';

export const authGuard: CanActivateFn = (route, state) => {
  const identityService = inject(Identity);
  const router = inject(Router);

  return identityService.currentUser$.pipe(
    map(user => {
      if (user) {
        return true;
      }
      router.navigate(['/Account/Login'], { queryParams: { returnUrl: state.url } });
      return false;
    })
  );
};
