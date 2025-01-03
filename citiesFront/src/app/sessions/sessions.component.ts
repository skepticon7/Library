import {Component, OnInit} from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {Session} from "../models/session.model";
import {SessionService} from "../services/session/session.service";
import {catchError, throwError} from "rxjs";
import {Duration, parse} from "tinyduration";
import {NgClass, NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-sessions',
  standalone: true,
  imports: [
    NavbarComponent,
    NgForOf,
    NgIf,
    NgClass,
    RouterLink
  ],
  templateUrl: './sessions.component.html',
  styleUrl: './sessions.component.css'
})
export class SessionsComponent implements OnInit{
  visitorId !: string;
  sessionData !: Array<Session>;
  errorMessage !: string;
  constructor(private route : ActivatedRoute , private router : Router , private sessionService : SessionService) {}

  ngOnInit(): void {
      this.route.queryParams.subscribe(param =>{
        this.visitorId = param["visitorId"];
        this.fetchSessions();
      })
  }

  fetchSessions(){
     this.sessionService.getSessions(this.visitorId).pipe(
       catchError(err => {
         this.errorMessage = err.error.message;
         return throwError(()=>new Error(err.message));
       })
     ).subscribe(response => {
       this.sessionData = response.map(data => ({
         ...data,
         originalTime : this.tinyDurationFormatter(parse(data.originalTime.toString())),
         remainingTime : this.tinyDurationFormatter(parse(data.remainingTime.toString()))
       }))
     });
  }

  private tinyDurationFormatter(duration : Duration){
    const hours = duration.hours ?? 0;
    const minutes = duration.minutes ?? 0;
    const seconds = duration.seconds ?? 0;
    return {hours , minutes , seconds};
  }

}
