import { Component } from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {AuthService} from "../services/auth/auth.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterLink,
    FormsModule,
    NgIf
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  keyword!: string;
  category : string = "";

  constructor(private router : Router , public authService : AuthService) {}


  HandleSearchForm() {
      if(this.category){
        const queryParams = {
          subName : this.keyword,
        }
        this.router.navigate([`/${this.category}`],{queryParams})
      }
  }

  handleLogout() {
    this.authService.logout();
    this.router.navigate([this.router.url]);
  }
}
