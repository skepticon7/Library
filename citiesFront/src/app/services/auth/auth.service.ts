import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import { FormGroup } from "@angular/forms";
import { environment } from "../../../environments/environment.development";
import {catchError, map, throwError} from "rxjs";
import {HotToastService} from "@ngxpert/hot-toast";
import {jwtDecode} from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isAuthenticated : boolean = false;
  accessToken !: string;
  roles !: string;
  username !: string;
  constructor(private http: HttpClient , private toaster : HotToastService) { }


  login(loginForm : FormGroup)  {
    return this.http.post<any>(`${environment.BackendServer}/auth/login` , loginForm.value);
  }

  loadProfile(res : string){
    this.isAuthenticated = true;
    this.accessToken = res;
    let decodedJwt = jwtDecode(this.accessToken) as {scope : string , sub : string};
    this.roles = decodedJwt.scope;
    this.username = decodedJwt.sub;
    console.log(decodedJwt);
    localStorage.setItem("jwt-token",this.accessToken);
  }


  signup(signupForm : FormGroup) {
    let params = new HttpParams().append("libraryId","e9935091-27ff-4a30-b30c-0d4c221d1eb7");
    return this.http.post<any>(`${environment.BackendServer}/auth/signup` , signupForm.value , {params}).pipe(
      catchError(err => {
        this.toaster.error(err.error.message);
        return throwError(()=>new Error(err));
      })
    ).subscribe(res => {
      console.log(res);
      this.toaster.success("user created");
    })
  }


  retrieveJwtFromLocalStorage(){
    let jwtToken = localStorage.getItem("jwt-token");
    return jwtToken;
  }

  logout() {
    this.isAuthenticated = false;
    this.roles = "";
    this.username = "";
    this.accessToken = "";
    localStorage.removeItem("jwt-token");
  }
}
