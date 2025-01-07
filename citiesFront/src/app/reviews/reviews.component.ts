import {Component, Input, OnInit} from '@angular/core';
import {ReviewsService} from "../services/reviews/reviews.service";
import {Observable} from "rxjs";
import {Review} from "../models/review.model";
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {AuthService} from "../services/auth/auth.service";

@Component({
  selector: 'app-reviews',
  standalone: true,
  imports: [
    AsyncPipe,
    NgIf,
    NgForOf
  ],
  templateUrl: './reviews.component.html',
  styleUrl: './reviews.component.css'
})
export class ReviewsComponent implements OnInit{
  reviews$ !: Observable<Review[]>
  @Input() bookId!: string;
  constructor(private reviewsService : ReviewsService , public authService : AuthService) {
  }

  ngOnInit(): void {
    this.reviews$ = this.reviewsService.getReviewsByBook(this.bookId);
  }

}
