import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {FormGroup} from "@angular/forms";
import {book, bookData} from "../../models/book.model";
import {environment} from "../../../environments/environment.development";
import {Observable} from "rxjs";
import {Shelf} from "../../models/shelf.model";

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private http : HttpClient) { }

  saveBook(shelfId : string , newBook : FormGroup ){
    let params = new HttpParams().append("shelfId",shelfId);
    return this.http.post<book>(`${environment.BackendServer}/book/addBook`,newBook,{params});
  }

  getBooks(filters?: HttpParams) {
    let params = new HttpParams();
    if (filters) {
      filters.keys().forEach(key => {
        params = params.append(key, filters.get(key) as string);
      });
    }
    if(params.get("shelfId"))
      return this.http.get<Array<book>>(`${environment.BackendServer}/book/filteredBooks`, { params });
    return this.http.get<Array<book>>(`${environment.BackendServer}/book/filteredLibrariesAll`, { params });
  }

  getSpecificBook(filters?:HttpParams){
    let params = new HttpParams();
    if(filters){
      filters.keys().forEach(key=>{
        params = params.append(key , filters.get(key) as string);
      })
    }
    return this.http.get<bookData>(`${environment.BackendServer}/book/getBook`,{params})
  }


}
