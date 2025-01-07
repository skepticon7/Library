import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NavbarComponent} from "../navbar/navbar.component";
import {NgForOf, NgIf} from "@angular/common";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {book} from "../models/book.model";
import {BookService} from "../services/Book/book.service";
import {HttpParams} from "@angular/common/http";
import {catchError, throwError} from "rxjs";
import {AuthService} from "../services/auth/auth.service";

@Component({
  selector: 'app-books',
  standalone: true,
    imports: [
        FormsModule,
        NavbarComponent,
        NgForOf,
        NgIf,
        ReactiveFormsModule,
        RouterLink
    ],
  templateUrl: './books.component.html',
  styleUrl: './books.component.css'
})
export class BooksComponent implements OnInit{
  booksData !: Array<book>;
  errorMessage!: string;
  bookFilterFormGroup! : FormGroup;
  minPagesValue !: number;
  maxPagesValue !: number;
  minYearValue !: number;
  maxYearValue !: number;
  orderType !: string;
  order !: string;
  shelfId !: string;
  subName !: string;

  constructor(public autService : AuthService,private fb : FormBuilder ,private bookService : BookService , private router : Router , private route : ActivatedRoute) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.order = params["order"] || "ASC";
      this.subName = params["subName"] || "";
      this.minPagesValue = params["minPages"] || 0;
      this.maxPagesValue = params["maxPages"] || 2000;
      this.minYearValue = params["minYear"] || 1;
      this.maxYearValue = params["maxYear"] || new Date().getFullYear();
      this.shelfId = params["shelfId"];

      this.bookFilterFormGroup = this.fb.group({
        minPagesValue: [this.minPagesValue],
        maxPagesValue: [this.maxPagesValue],
        minYearValue: [this.minYearValue],
        maxYearValue: [this.maxYearValue]
      });

      this.fetchBooks();
    });
  }

  handleFiltersBookOptions(event : any) {
    let [orderType , order] = event.target.value.split(",");
    this.orderType = orderType;
    this.order = order;
    this.router.navigate([],{
      relativeTo : this.route,
      queryParams : {order : order , orderType : orderType},
      queryParamsHandling : 'merge'
    })
  }

  handleBookFiltersForm() {
    const filtersForm = this.bookFilterFormGroup.value;
    console.log(filtersForm);
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {
        minPages: filtersForm.minPagesValue,
        maxPages: filtersForm.maxPagesValue,
        minYear: filtersForm.minYearValue,
        maxYear: filtersForm.maxYearValue
      },
      queryParamsHandling: "merge"
    });
  }

  fetchBooks() : void {
    let params = new HttpParams()
      .append("order",this.order)
      .append("orderType",this.orderType)
      .append("minPages",this.minPagesValue)
      .append("maxPages",this.maxPagesValue)
      .append("minYear",this.minYearValue)
      .append("maxYear",this.maxYearValue)
      .append("subName",this.subName);

      if (this.shelfId) {
        params = params.append("shelfId", this.shelfId);
      }

      this.bookService.getBooks(params).pipe(
        catchError(err => {
          this.errorMessage = err.error.message || "unable to connect to the server , please try again later";
          return throwError(()=>new Error(err.error.message));
        })
      ).subscribe(data => {
        this.booksData = data;
        this.minYearValue = Math.min(...data.map(b => b.year));
        this.maxYearValue = Math.max(...data.map(b => b.year));
        this.minPagesValue = Math.min(...data.map(b => b.pages));
        this.maxPagesValue = Math.max(...data.map(b => b.pages));

        this.bookFilterFormGroup = this.fb.group({
          minPagesValue : this.minPagesValue,
          maxPagesValue : this.maxPagesValue,
          minYearValue : this.minYearValue,
          maxYearValue : this.maxYearValue
        })

      })


  }

}
