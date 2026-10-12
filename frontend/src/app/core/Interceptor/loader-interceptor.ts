import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { finalize } from 'rxjs';
import { LoadingService } from '../Services/loading';

export const loaderInterceptor: HttpInterceptorFn = (req, next) => {
  const loadingService = inject(LoadingService);

  // تشغيل الـ Spinner مع بداية الـ Request
  loadingService.busy();

  return next(req).pipe(
    // قفل الـ Spinner بمجرد انتهاء الـ Request سواء بنجاح أو بخطأ
    finalize(() => {
      loadingService.idle();
    })
  );
};
