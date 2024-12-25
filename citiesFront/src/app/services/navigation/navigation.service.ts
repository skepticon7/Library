import { Injectable } from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {filter} from "rxjs";
import * as events from "node:events";

@Injectable({
  providedIn: 'root'
})
export class NavigationService {
    private previousRoute !: string;
    private currentRoute !: string;
  constructor(private router : Router) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.previousRoute = this.currentRoute;
        this.currentRoute = event.url;
      }
    });
  }


  public getPreviousRoute() : string{
    return this.previousRoute;
  }

  public getCurrentRoute() : string {
    return this.currentRoute;
  }

}
