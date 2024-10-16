import { Component } from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-cancel',
  standalone: true,
  imports: [
    NavbarComponent,
    RouterLink
  ],
  templateUrl: './cancel.component.html',
  styleUrl: './cancel.component.css'
})
export class CancelComponent {

}
