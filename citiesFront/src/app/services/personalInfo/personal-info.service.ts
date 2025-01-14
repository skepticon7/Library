import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {FormGroup} from "@angular/forms";
import {Observable} from "rxjs";
import {Person} from "../../models/person.model";
import {environment} from "../../../environments/environment.development";

@Injectable({
  providedIn: 'root'
})
export class PersonalInfoService {

  constructor(private http : HttpClient) { }

  public updatePersonalInfo(personId : string , personalData : FormGroup){
    let params = new HttpParams().append("personId" , personId);
    return this.http.patch(`${environment.BackendServer}/person/updatePersonalInfo` , personalData.value , {params});
  }



}
