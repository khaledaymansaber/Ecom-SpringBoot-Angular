import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const toastr = inject(ToastrService);
  const router = inject(Router);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error) {
        if (error.status === 400) {
          const msg = error.error?.message || error.error?.title || 'Bad Request';
          toastr.error(msg, 'Error');
        } else if (error.status === 401 || error.status === 403) {
          toastr.warning('Please log in to continue', 'Unauthorized');
          router.navigateByUrl('/Account/Login');
        } else if (error.status === 404) {
          const msg = error.error?.message || 'Resource not found';
          toastr.error(msg, 'Not Found');
        } else if (error.status === 500) {
          toastr.error('An unexpected server error occurred', 'Server Error');
        }
      }
      return throwError(() => error);
    })
  );
};
