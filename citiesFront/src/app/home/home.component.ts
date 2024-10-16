import { Component } from '@angular/core';
import {HeroComponent} from "../hero/hero.component";
import {NavbarComponent} from "../navbar/navbar.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    HeroComponent,
    NavbarComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
