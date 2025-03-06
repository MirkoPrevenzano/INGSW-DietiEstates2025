import { ApplicationConfig, importProvidersFrom, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';

import { routes } from './app.routes';
import { authInterceptor } from './_interceptor/authentication.interceptor';
import { BrowserModule } from '@angular/platform-browser';
import { GoogleMapsModule } from '@angular/google-maps';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { SlickCarouselModule } from 'ngx-slick-carousel';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MatDialogModule } from '@angular/material/dialog';
import { AgChartsModule } from 'ag-charts-angular';
export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }), 
    provideRouter(routes),
    provideHttpClient(
      withFetch(), // use the Fetch API instead of XMLHttpRequests
      withInterceptors([authInterceptor])
    ),
    importProvidersFrom(BrowserModule),
    importProvidersFrom(GoogleMapsModule),
    importProvidersFrom(BrowserAnimationsModule),
    importProvidersFrom(FormsModule),
    importProvidersFrom(ReactiveFormsModule),
    importProvidersFrom(ToastrModule.forRoot()),
    importProvidersFrom(SlickCarouselModule), 
    provideAnimationsAsync(),
    importProvidersFrom(MatDialogModule),
    importProvidersFrom(AgChartsModule),

    
    
  ]
};