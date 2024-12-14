import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHotToastConfig } from '@ngxpert/hot-toast';



import { routes } from './app.routes';
import {provideHttpClient} from "@angular/common/http";
import {DatePipe} from "@angular/common";

export const appConfig: ApplicationConfig = {
  providers: [provideZoneChangeDetection({ eventCoalescing: true }), provideRouter(routes) , provideHotToastConfig(),provideHttpClient() , [DatePipe]]
};
