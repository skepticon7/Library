import {Component, OnInit} from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {City} from "../models/city.model";
import {CityService} from "../services/city/city.service";
import {catchError, Observable, throwError} from "rxjs";
import {AsyncPipe, NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-cities',
  standalone: true,
  imports: [
    NavbarComponent,
    RouterLink,
    AsyncPipe,
    NgIf,
    NgForOf,
    NgOptimizedImage,
    FormsModule
  ],
  templateUrl: './cities.component.html',
  styleUrl: './cities.component.css'
})
export class CitiesComponent implements OnInit{

  //variable for binding
  CitiesData! : Array<City>;
  ErrorMessage! : string;
  order! : string;
  subName! : string;

  //city service injection
  constructor(private router : Router,private citiesService : CityService , private route : ActivatedRoute) {
  }

  ngOnInit(): void {
       this.route.queryParams.subscribe(params => {
         this.order = params["order"];
         this.subName = params["subName"] || "";
         this.fetchCities();
       })
  }


  fetchCities() : void {
    if(this.order){
     this.citiesService.getCitiesOrder(this.order,this.subName).pipe(
        catchError(err => {
          this.ErrorMessage = err.message;
          return throwError(() => new Error(err.message));
        })
      ).subscribe(data => {
        this.CitiesData = data;
     });
    }else{
      this.citiesService.getCities(this.subName).pipe(
        catchError(err => {
          this.ErrorMessage = err.error.message || "unable to connect to the server , please try again later";
          return throwError(()=>new Error(err.message));
        })
      ).subscribe(data => {
        this.CitiesData = data;
      })
    }

  }


  handleFilters(event : any) {
    let filter : string = event.target.value;
    this.order = filter;
    this.router.navigate([],{
      relativeTo : this.route,
      queryParams : {"order" : filter},
      queryParamsHandling : "merge"
    })
  }

}
