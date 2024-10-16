import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Library} from "../../models/library.model";
import {Shelf} from "../../models/shelf.model";
import {environment} from "../../../environments/environment.development";
import {Observable} from "rxjs";
import {FormGroup} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class ShelfService {

  constructor(private http : HttpClient) { }

  getShelves(filters?: HttpParams) {
      let params = new HttpParams();
      filters?.keys().forEach(key=>{
        params = params.append(key , filters?.get(key) as string);
      })
      return this.http.get<Array<Shelf>>(`${environment.BackendServer}/shelf/filteredShelves`,{params})
  }

  saveShelf(libraryId: string, newShelfForm: FormGroup) {
    let params = new HttpParams().append("libraryId",libraryId);
    return this.http.post<Shelf>(`${environment.BackendServer}/shelf/addShelf`,newShelfForm , {params});
  }
}
