import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {filter, Observable} from "rxjs";
import {Library} from "../../models/library.model";
import {environment} from "../../../environments/environment.development";

@Injectable({
  providedIn: 'root'
})
export class LibrariesService {

  constructor(private http : HttpClient) { }



  saveLibrary(cityId : string , newLibrary : Library) : Observable<Library> {
    let params = new HttpParams();
    params = params.append("cityId",cityId);
    return this.http.post<Library>(`${environment.BackendServer}/libraries/addLibrary`,newLibrary,{params});
  }

  getLibraries(filters?:HttpParams) {
    let params = new HttpParams()
    if (filters) {
      filters.keys().forEach(key => {
        params = params.append(key, filters.get(key) as string);
      });
    }
    return this.http.get<Array<Library>>(`${environment.BackendServer}/libraries/filterLibrariesByCity`,{params})
  }

  getAllLibrariesByFilters(filters?:HttpParams) {
    let params = new HttpParams();
    if(filters){
      filters.keys().forEach(key => {
        params = params.append(key , filters.get(key) as string);
      })
    }
    return this.http.get<Array<Library>>(`${environment.BackendServer}/libraries/filteredLibraries`,{params});
  }





}
