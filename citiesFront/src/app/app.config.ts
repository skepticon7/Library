import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHotToastConfig } from '@ngxpert/hot-toast';
import {InterceptorComponent} from "./interceptor/interceptor.component";

import { routes } from './app.routes';
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi} from "@angular/common/http";
import {DatePipe} from "@angular/common";

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes) ,
    provideHotToastConfig(),
    provideHttpClient(
      withInterceptorsFromDi()
    ),
    {provide : HTTP_INTERCEPTORS , useClass : InterceptorComponent  , multi : true} ,
    [DatePipe]
  ]
};
