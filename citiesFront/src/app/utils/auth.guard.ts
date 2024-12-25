import {CanActivateFn, Router} from '@angular/router';
import {inject, Inject} from "@angular/core";
import {AuthService} from "../services/auth/auth.service";

export const authGuard: CanActivateFn = () : boolean => {
  const route : Router = inject(Router);
  const authService : AuthService = inject(AuthService);
  if(!authService.isAuthenticated()){
    route.navigateByUrl("/login");
    return false;
  }
  return true;
};
