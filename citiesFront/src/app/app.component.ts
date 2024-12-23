import {Component, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {AuthService} from "./services/auth/auth.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,HomeComponent ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  constructor(private authService : AuthService) {}
    ngOnInit(): void {
          let jwtData = this.authService.retrieveJwtFromLocalStorage();
          if(jwtData)
            this.authService.loadProfile(jwtData);
    }
}
