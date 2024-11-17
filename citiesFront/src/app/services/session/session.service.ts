import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Session} from "../../models/session.model";
import {environment} from "../../../environments/environment.development";

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor(private http : HttpClient) { }

  getSessions(visitorId : string){
    let params = new HttpParams().append("visitorId",visitorId);
    return this.http.get<Array<Session>>(`${environment.BackendServer}/session/getVisitorSessions`,{params})
  }

  getSession(sessionId : string) {
    let params = new HttpParams().append("sessionId",sessionId);
    return this.http.get<Session>(`${environment.BackendServer}/session/getSession`,{params});
  }

}
