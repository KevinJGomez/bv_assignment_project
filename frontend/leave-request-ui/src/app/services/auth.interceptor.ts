import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthServiceService } from './auth-service.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthServiceService);

  // Clone the request to add the Content-Type header
  let authReq = req.clone({
    setHeaders: {
      'Content-Type': 'application/json'
    }
  });

  // Conditionally add the Authorization header
  if (!req.url.endsWith('/login') && !req.url.endsWith('/register')) {
    const authToken = authService.getToken();
    authReq = authReq.clone({
      setHeaders: {
        Authorization: `Bearer ${authToken}`
      }
    });
  }

  return next(authReq);
};
