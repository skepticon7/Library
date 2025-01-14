import { Component } from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {BookmarksComponent} from "./bookmarks/bookmarks.component";
import {ReviewsComponent} from "./reviews/reviews.component";
import {SessionsComponent} from "./sessions/sessions.component";
import {PersonalInfoComponent} from "./personal-info/personal-info.component"
import {NgIf} from "@angular/common";
import {AuthService} from "../services/auth/auth.service";
@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [NavbarComponent, SessionsComponent, PersonalInfoComponent, BookmarksComponent, ReviewsComponent, BookmarksComponent, NgIf],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  selectedOption : string = 'personalInfo';

  constructor(public authService : AuthService) {}

  selectOption(option: string) {
    this.selectedOption = option;
  }
}
