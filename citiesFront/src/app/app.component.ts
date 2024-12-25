import {Component, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {AuthService} from "./services/auth/auth.service";
import {NavigationService} from "./services/navigation/navigation.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,HomeComponent ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent{
  constructor(private authService : AuthService) {}

}
