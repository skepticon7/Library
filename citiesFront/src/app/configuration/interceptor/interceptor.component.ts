import { Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {catchError, Observable, throwError} from 'rxjs';
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth/auth.service";

@Injectable()
export class InterceptorComponent implements HttpInterceptor {
    private excludedRoutes : string[] = ['/login' , '/signup' , '/' , '/cities' , '/libraries' , '/shelves' , '/books' , '/book'];
    constructor(private router : Router , private authService : AuthService) {}
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      if(this.isExcluded(req.url))
        return next.handle(req);
      const cloneRequest = req.clone({
        setHeaders : {
          Authorization : `Bearer  ${this.authService.retrieveJwtFromLocalStorage()} || " "`
        }
      })
      return next.handle(cloneRequest).pipe(
        catchError((error: HttpErrorResponse) => {
          if(error.status == 401){
            this.router.navigate(['/unauthorized']);
          }
          return throwError(() => new Error(error.message));
        })
      );
  }

  private isExcluded(route : string) {
    console.log("hey");
      return this.excludedRoutes.some(url => url.includes(route));
  }


}
