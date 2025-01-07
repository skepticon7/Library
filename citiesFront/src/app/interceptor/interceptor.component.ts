import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {catchError, Observable, throwError} from 'rxjs';
import {AuthService} from "../services/auth/auth.service";
import {Router} from "@angular/router";

@Injectable()
export class InterceptorComponent implements HttpInterceptor {
    private includedRoutes : string[] = ['/addShelf' , '/addLibrary' , '/addCity' , '/addBook' ,'/pdfBook' , '/librarian/newCity' , '/librarian/newShelf' ,'/librarian/newBook' , '/librarian/newLibrary'];

    constructor(private authService : AuthService , private router : Router) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      if(!this.isIncluded(req.url))
          return next.handle(req);

        let clonedRequest = req.clone({
          setHeaders :  {
            Authorization : `Bearer ${this.authService.retrieveJwtFromLocalStorage() || " "}`
          }
        });
        return next.handle(clonedRequest).pipe(
          catchError((err : HttpErrorResponse) => {
            if(err.status == 401){
              this.router.navigateByUrl("/unauthorized");
            }
            return throwError(()=>new Error(err.message));
          })
        );
    }


    private isIncluded(route : string) : boolean {
      return this.includedRoutes.some(includedRoute => {
        return route.includes(includedRoute);
      });
    }

}
