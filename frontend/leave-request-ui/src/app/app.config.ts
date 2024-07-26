import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { authInterceptor } from './services/auth.interceptor';
import { withInterceptors } from '@angular/common/http';
import { AuthServiceService } from './services/auth-service.service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(), // Add this line to provide HttpClientModule
    provideClientHydration(),
    provideHttpClient(withInterceptors([authInterceptor])),
    AuthServiceService
  ]
};
