import {Component, computed, Input, OnInit, signal, Signal} from '@angular/core';
import {ReviewsService} from "../services/reviews/reviews.service";
import {catchError, Observable, throwError} from "rxjs";
import {Review} from "../models/review.model";
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {AuthService} from "../services/auth/auth.service";
import {Form, FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {HotToastService} from "@ngxpert/hot-toast";

@Component({
  selector: 'app-reviews',
  standalone: true,
  imports: [
    AsyncPipe,
    NgIf,
    NgForOf,
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './reviews.component.html',
  styleUrl: './reviews.component.css'
})
export class ReviewsComponent implements OnInit{
  reviews$ !: Observable<Review[]>
  addReviewToggled = signal(false);
  reviewForm !: FormGroup ;

  @Input() bookId!: string;
  constructor(private toaster : HotToastService , private reviewsService : ReviewsService , public authService : AuthService , private fb : FormBuilder) {}

  ngOnInit(): void {
    this.reviews$ = this.reviewsService.getReviewsByBook(this.bookId);
  }

  handleAddReviewToggled() : void {
    if(!this.authService.isAuthenticated()){
      this.toaster.error("You have to be logged in");
      return;
    }
    this.addReviewToggled.update(value => !value);
    if(this.addReviewToggled()){
      this.reviewForm = this.fb.nonNullable.group({
        review: ['', [Validators.required, Validators.minLength(5)]],
        stars: [1, [Validators.required, Validators.min(1), Validators.max(5)]]
      });
    }
  }

  submitReview() {
    const userId : string | undefined = this.authService.getCurrentUser()?.id;
    console.log(userId);
    console.log(this.bookId);
    if(this.reviewForm.valid && this.addReviewToggled() && userId){
      this.reviewsService.addReview(this.bookId , userId , this.reviewForm.value).pipe(
        catchError(err => {
          return throwError(()=>new Error(err.message));
        })
      ).subscribe(()=>{
        this.toaster.success("review submitted");
        this.reviewForm.reset();
        this.addReviewToggled.update(value => !value);
      });
    }
  }

}
