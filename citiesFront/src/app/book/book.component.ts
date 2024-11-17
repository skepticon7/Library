import {Component, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NavbarComponent} from "../navbar/navbar.component";
import {NgClass, NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {BookService} from "../services/Book/book.service";
import {book, bookData} from "../models/book.mode";
import {HttpParams} from "@angular/common/http";
import {catchError, throwError} from "rxjs";
import {StripeService} from "../services/stripe/stripe.service";
import {HotToastService} from "@ngxpert/hot-toast";

@Component({
  selector: 'app-book',
  standalone: true,
  imports: [
    FormsModule,
    NavbarComponent,
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    RouterLink,
    NgClass,
    NgOptimizedImage
  ],
  templateUrl: './book.component.html',
  styleUrl: './book.component.css'
})
export class BookComponent implements OnInit{
    bookId!: string;
    shelfId!: string;
    visitorId : string = "06832314-909f-47b9-806a-521922c79eb7";
    bookData !: bookData;
    errorMessage !: string;
    bookPrice!: number;
    selectedSession : string = 'halfPrice';

    constructor(private toaster : HotToastService,private route : ActivatedRoute , private router : Router , private bookService : BookService , private stripeService : StripeService) {}

    ngOnInit(): void {
      this.route.queryParams.subscribe(params =>{
        this.bookId = params["bookId"];
        this.shelfId = params["shelfId"];
        // this.visitorId = params["visitorId"];
        this.fetchBook();
      })
    }

    fetchBook(){
      let params = new HttpParams().append("shelfId",this.shelfId)
        .append("bookId",this.bookId)
        .append("visitorId",this.visitorId);
      this.bookService.getSpecificBook(params).pipe(
        catchError(err => {
          this.errorMessage = err.error.message || "unable to connect to the server , please try again later";
          return throwError(()=>new Error(err.message));
        })
      ).subscribe(data => {
        this.bookData = data;
        this.bookPrice = data.book.halfPrice;
      })
    }


  setBookPrice(price: string) {
    switch (price){
      case 'half':this.bookPrice = this.bookData.book.halfPrice;
                  this.selectedSession = 'halfPrice';
                  break;
      case 'one':this.bookPrice = this.bookData.book.onePrice;
                  this.selectedSession = 'onePrice';
                  break;
      case 'oneHalf':this.bookPrice = this.bookData.book.oneHalfPrice;
                    this.selectedSession = 'oneHalfPrice';
                    break;
    }
  }

  handleSessionPayment(book : book ,price : number , sessionType : string){
      const payment = {
        amount : price*100,
        customer : "14e14bb2-56eb-47f3-85ce-d0a29e3a7e9d",
        book : book,
        successUrl : 'http://localhost:4200/success',
        cancelUrl : 'http://localhost:4200/cancel',
        sessionType : sessionType
      }
    this.stripeService.createCheckoutSession(payment);

  }

}
