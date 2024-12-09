import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { FormGroup } from "@angular/forms";
import { environment } from "../../../environments/environment.development";
import {catchError, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isAuthenticated : boolean = false;
  constructor(private http: HttpClient) { }
  login(loginForm : FormGroup)  {
    this.http.post<any>(`${environment.BackendServer}/auth/login` , loginForm.value).pipe(
      catchError(err => {
        return throwError(()=>new Error(err));
      })
    ).subscribe(res => {
      this.isAuthenticated = true;
      let loggedInUser = {
        jwtToken : res["jwt-token"],
        roles : res["roles"]
      }
      console.log(loggedInUser);
      localStorage.setItem("user-info",JSON.stringify(loggedInUser));
    })
  }
}
