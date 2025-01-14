import { Component } from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {AuthService} from "../services/auth/auth.service";
import {NgIf, NgOptimizedImage} from "@angular/common";
import {NavigationService} from "../services/navigation/navigation.service";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterLink,
    FormsModule,
    NgIf,
    NgOptimizedImage
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  keyword!: string;
  category : string = "";

  constructor(private router : Router , public authService : AuthService , private navigationService : NavigationService) {}


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
    this.router.navigateByUrl(this.navigationService.getCurrentRoute());
  }
}
