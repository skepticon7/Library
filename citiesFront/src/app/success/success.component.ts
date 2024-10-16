import { Component } from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-success',
  standalone: true,
  imports: [
    NavbarComponent,
    RouterLink
  ],
  templateUrl: './success.component.html',
  styleUrl: './success.component.css'
})
export class SuccessComponent {

}
