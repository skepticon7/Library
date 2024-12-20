import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import { FormGroup } from "@angular/forms";
import { environment } from "../../../environments/environment.development";
import {catchError, map, throwError} from "rxjs";
import {HotToastService} from "@ngxpert/hot-toast";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isAuthenticated : boolean = false;
  constructor(private http: HttpClient , private toaster : HotToastService) { }


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


  signup(signupForm : FormGroup) {
    let params = new HttpParams().append("libraryId","e9935091-27ff-4a30-b30c-0d4c221d1eb7");
    this.http.post<any>(`${environment.BackendServer}/auth/signup` , signupForm.value , {params}).pipe(
      catchError(err => {
        this.toaster.error(err.error.message);
        return throwError(()=>new Error(err));
      })
    ).subscribe(res => {
      console.log(res);
      this.toaster.success("user created");
    })
  }


  public retrieveJwtFromLocalStorage(){
    let userData = localStorage.getItem("user-info");
    return userData;
  }

}
