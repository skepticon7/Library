import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Review} from "../../models/review.model";
import {environment} from "../../../environments/environment.development";

@Injectable({
  providedIn: 'root'
})
export class ReviewsService {

  constructor(private Http : HttpClient) { }

  public getReviewsByBook(bookId : string) : Observable<Review[]> {
    let params = new HttpParams().append("bookId",bookId);
    return this.Http.get<Review[]>(`${environment.BackendServer}/reviews/getByBook`,{params});
  }

  public addReview(bookId : string , personId : string) : Observable<Review> {
    let params = new HttpParams();
    params.append("bookId" , bookId);
    params.append("personId" , personId);
    return this.Http.post<Review>(`${environment.BackendServer}/reviews/addReview`,{params});
  }

}
