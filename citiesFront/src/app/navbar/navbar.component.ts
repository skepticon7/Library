import { Component } from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterLink,
    FormsModule
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  keyword!: string;
  category : string = "";

  constructor(private router : Router) {}


  HandleSearchForm() {
      if(this.category){
        const queryParams = {
          subName : this.keyword,
        }
        this.router.navigate([`/${this.category}`],{queryParams})
      }
  }
}
