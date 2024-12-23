import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {LibrariesService} from "../services/libraries/libraries.service";
import {catchError, filter, Observable, throwError} from "rxjs";
import {Library} from "../models/library.model";
import {NavbarComponent} from "../navbar/navbar.component";
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpParams} from "@angular/common/http";
import {appConfig} from "../app.config";
import {AuthService} from "../services/auth/auth.service";


@Component({
  selector: 'app-libraries',
  standalone: true,
  imports: [
    NavbarComponent,
    RouterLink,
    AsyncPipe,
    NgIf,
    NgForOf,
    FormsModule,
    ReactiveFormsModule,
  ],
  templateUrl: './libraries.component.html',
  styleUrl: './libraries.component.css'
})
export class LibrariesComponent implements OnInit{

  librariesData! : Array<Library>
  libraryFilterForm !: FormGroup;
  errorMessage! : string;
  cityId: any;
  order! : string;
  orderType! : string;
  subName! : string;
  minCapacityValue! : number;
  maxCapacityValue! : number;
  minShelvesValue! : number;
  maxShelvesValue! : number;



  constructor(public authService : AuthService , private fb : FormBuilder , private router : Router,private route : ActivatedRoute , private libraryService : LibrariesService) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params =>{
      this.cityId = params["cityId"];
      this.order = params["order"] || 'ASC';
      this.orderType = params["orderType"];
      this.subName = params["subName"];
      this.minShelvesValue = params["minShelves"] || 0;
      this.maxCapacityValue = params["maxCapacity"] || 200;
      this.maxShelvesValue = params["maxShelves"] || 25;
      this.minCapacityValue = params["minCapacity"] || 0;
      this.libraryFilterForm = this.fb.group({
        minCapacityValue : this.minCapacityValue ,
        maxCapacityValue : this.maxCapacityValue ,
        minShelvesValue : this.minShelvesValue ,
        maxShelvesValue : this.maxShelvesValue
      })
      this.fetchLibraries();
    })
  }


  fetchLibraries(): void {
    let params = new HttpParams()
      .append("minShelves", this.minShelvesValue)
      .append("maxShelves", this.maxShelvesValue)
      .append("minCapacity",this.minCapacityValue)
      .append("maxCapacity",this.maxCapacityValue)
      .append("order",this.order)
      .append("orderType",this.orderType)
      .append("cityId",this.cityId);

      if(this.cityId){
        this.libraryService.getLibraries(params).pipe(
          catchError(err => {
            console.log("reached here in pipe catch error library");
            this.errorMessage = err.error.message || "unable to connect to the server , please try again later";
            return throwError(()=>new Error(err.message));
          })
        ).subscribe(data => {
          console.log("reached here in subscribe library");
          this.librariesData = data;
          this.minShelvesValue = Math.min(...data.map(s => s.shelvesCapacity));
          this.maxShelvesValue = Math.max(...data.map(s => s.shelvesCapacity));
          this.minCapacityValue = Math.min(...data.map(s => s.visitorsCapacity));
          this.maxCapacityValue = Math.max(...data.map(s => s.visitorsCapacity));

          // Initialize the form group with the calculated values
          this.libraryFilterForm = this.fb.group({
            minCapacityValue: this.minCapacityValue,
            maxCapacityValue: this.maxCapacityValue,
            minShelvesValue: this.minShelvesValue,
            maxShelvesValue: this.maxShelvesValue
          });
        });
      }else{
        params = params.append("subName",this.subName);
        this.libraryService.getAllLibrariesByFilters(params).pipe(
          catchError(err => {
            console.log("reached here in pipe catch error library");
            this.errorMessage = err.error.message;
            return throwError(()=>new Error(err.message));
          })
        ).subscribe(data => {

          this.librariesData = data;
          this.minShelvesValue = Math.min(...data.map(s => s.shelvesCapacity));
          this.maxShelvesValue = Math.max(...data.map(s => s.shelvesCapacity));
          this.minCapacityValue = Math.min(...data.map(s => s.visitorsCapacity));
          this.maxCapacityValue = Math.max(...data.map(s => s.visitorsCapacity));

          // Initialize the form group with the calculated values
          this.libraryFilterForm = this.fb.group({
            minCapacityValue: this.minCapacityValue,
            maxCapacityValue: this.maxCapacityValue,
            minShelvesValue: this.minShelvesValue,
            maxShelvesValue: this.maxShelvesValue
          });
        })
      }


  }


  handleFiltersLibrary(event : any) {
    const [orderType , order] = event.target.value.split(",");
    this.order = order;
    this.orderType = orderType;
    this.router.navigate([],{
      relativeTo : this.route,
      queryParams : {order : order , orderType : orderType},
      queryParamsHandling : 'merge'
    })
  }

  handleFilterForm(): void {
    let filterForm = this.libraryFilterForm.value;
    this.router.navigate([],{
      relativeTo : this.route,
      queryParams : {minShelves : filterForm.minShelvesValue , maxShelves : filterForm.maxShelvesValue , minCapacity : filterForm.minCapacityValue , maxCapacity : filterForm.maxCapacityValue},
      queryParamsHandling : "merge"
    })
    this.fetchLibraries();
  }


}
