import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import { FormGroup } from "@angular/forms";
import { environment } from "../../../environments/environment.development";
import {BehaviorSubject, catchError, filter, map, Observable, throwError} from "rxjs";
import {HotToastService} from "@ngxpert/hot-toast";
import {jwtDecode} from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  accessToken !: string;

  private currentUserSubject : BehaviorSubject<{username : string , roles : string} | null> = new BehaviorSubject<{username : string , roles : string} | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient , private toaster : HotToastService) {
    if(this.retrieveJwtFromLocalStorage())
      this.loadProfile(this.retrieveJwtFromLocalStorage() as string);

  }


  login(loginForm : FormGroup)  {
    return this.http.post<any>(`${environment.BackendServer}/auth/login` , loginForm.value);
  }

  loadProfile(token : string){
    try{
      let decodedJwt = jwtDecode(token) as {scope : string , sub : string};
      let roles : string=  decodedJwt.scope;
      let username : string = decodedJwt.sub;
      let user: {username : string , roles : string} = {username , roles};
      this.currentUserSubject.next(user);
      this.accessToken = token;
      localStorage.setItem("jwt-token",this.accessToken);
    }catch {
      this.currentUserSubject.next(null);
    }
  }


  signup(signupForm : FormGroup) {
    let params = new HttpParams().append("libraryId","e9935091-27ff-4a30-b30c-0d4c221d1eb7");
    return this.http.post<any>(`${environment.BackendServer}/auth/signup` , signupForm.value , {params}).pipe(
      catchError(err => {
        this.toaster.error(err.error.message);
        return throwError(()=>new Error(err));
      })
    ).subscribe(res => {
      this.toaster.success("user created");
    })
  }


  retrieveJwtFromLocalStorage() : string | null{
    let jwtToken : string | null = localStorage.getItem("jwt-token");
    if(jwtToken)
      return jwtToken;
    return null;
  }

  logout() {
    this.currentUserSubject.next(null);
    console.log("hey");
    this.accessToken = "";
    localStorage.removeItem("jwt-token");
  }

  getCurrentUser() : {username : string , roles : string} | null{
    return this.currentUserSubject.value;
  }


  isAuthenticated() : boolean {
    return this.currentUserSubject.value !== null;
  }
}
