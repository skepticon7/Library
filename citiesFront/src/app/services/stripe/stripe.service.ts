import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {loadStripe} from "@stripe/stripe-js";
import {environment} from "../../../environments/environment.development";
import {stripeResponse} from "../../models/stripeResponse.model";
import {catchError, throwError} from "rxjs";
import {HotToastService} from "@ngxpert/hot-toast";

@Injectable({
  providedIn: 'root'
})
export class StripeService {

  private stripePromise = loadStripe(environment.stripe);

  constructor(private http : HttpClient , private toaster : HotToastService) { }

  async createCheckoutSession(payment : any) : Promise<void> {
    const stripe = await this.stripePromise;
    this.http.post<stripeResponse>(`${environment.BackendServer}/stripe/payment`,payment).pipe(
      this.toaster.observe({
        loading : "Preparing gateway ...",
        success : "gateway ready",
        error : "Could not prepare gateway"

      })
      ,catchError(err => {
        return throwError(()=>new Error(err.message));
      })
    ).subscribe(data => {
      stripe?.redirectToCheckout({
        sessionId : data.id
      })
    })
  }

}
