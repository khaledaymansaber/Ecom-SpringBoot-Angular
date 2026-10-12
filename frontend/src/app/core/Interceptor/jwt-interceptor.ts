import { HttpInterceptorFn } from '@angular/common/http';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token');

  let headers = req.headers;
  if (token) {
    headers = headers.set('Authorization', `Bearer ${token}`);
  }

  // Always enable withCredentials: true so the HTTP-only cookie 'token' is sent with requests
  const cloned = req.clone({
    headers,
    withCredentials: true
  });

  return next(cloned);
};
