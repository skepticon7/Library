import {Component, OnInit} from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {AsyncPipe, NgFor, NgForOf, NgIf} from "@angular/common";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {ShelfService} from "../services/shelf/shelf.service";
import {Shelf} from "../models/shelf.model";
import {HttpParams} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {categoryImageLinkGenerator} from "../utils/Category.utils";
import {AuthService} from "../services/auth/auth.service";

@Component({
  selector: 'app-shelves',
  standalone: true,
  imports: [
    NavbarComponent,
    ReactiveFormsModule,
    AsyncPipe,
    NgIf,
    NgForOf,
    RouterLink
  ],
  templateUrl: './shelves.component.html',
  styleUrl: './shelves.component.css'
})
export class ShelvesComponent implements OnInit{
  shelfFormGroup!: FormGroup;
  minCapacityValue!: number;
  maxCapacityValue!: number;
  minOccupiedValue! : number;
  maxOccupiedValue! : number;
  shelvesData!:  Array<Shelf>;
  errorMessage!: string;
  libraryId!:string;
  order!:string;
  orderType!:string;

  constructor(public authService : AuthService , private fb : FormBuilder , private router : Router , private route : ActivatedRoute , private shelfService : ShelfService) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params =>{
      this.libraryId = params["libraryId"];
      this.order = params["order"] || 'ASC';
      this.orderType = params["orderType"];
      this.maxOccupiedValue = params["maxOccupied"] || 200;
      this.minOccupiedValue = params["minOccupied"] || 0;
      this.minCapacityValue = params["minCapacity"] || 0;
      this.maxCapacityValue = params["maxCapacity"] || 200;
      this.shelfFormGroup = this.fb.group({
        minCapacityValue : this.minCapacityValue,
        maxCapacityValue : this.maxCapacityValue,
        minOccupiedValue : this.minOccupiedValue,
        maxOccupiedValue : this.maxOccupiedValue
      })
      this.fetchShelves();
    })
  }

  handleShelfFilter() {
      let shelfFilters = this.shelfFormGroup.value;
      this.router.navigate([],{
        relativeTo : this.route,
        queryParams : {
          minOccupied : shelfFilters.minOccupiedValue,
          maxOccupied : shelfFilters.maxOccupiedValue,
          minCapacity : shelfFilters.minCapacityValue,
          maxCapacity : shelfFilters.maxCapacityValue
        },
        queryParamsHandling : 'merge'
      })
  }

  private fetchShelves() : void {
      let params = new HttpParams()
        .append("order",this.order)
        .append("orderType",this.orderType)
        .append("libraryId",this.libraryId)
        .append("minOccupied",this.minOccupiedValue)
        .append("maxOccupied",this.maxOccupiedValue)
        .append("minCapacity",this.minCapacityValue)
        .append("maxCapacity",this.maxCapacityValue);

     this.shelfService.getShelves(params).pipe(
        catchError(err => {
          this.errorMessage = err.error.message || "unable to connect to the server , please try again later";
          return throwError(()=>new Error(err.message));
        })
      ).subscribe(data => {
          this.shelvesData = data;
       this.minOccupiedValue = Math.min(...data.map(s => s.currentBooks));
       this.maxOccupiedValue = Math.max(...data.map(s => s.currentBooks));
       this.minCapacityValue = Math.min(...data.map(s => s.booksCapacity));
       this.maxCapacityValue = Math.max(...data.map(s => s.booksCapacity));

       // Initialize the form group with the calculated values
       this.shelfFormGroup = this.fb.group({
         minCapacityValue: this.minCapacityValue,
         maxCapacityValue: this.maxCapacityValue,
         minOccupiedValue: this.minOccupiedValue,
         maxOccupiedValue: this.maxOccupiedValue
       });
      })

  }

  handleFiltersShelves(event: any) {
      let [orderType , order] = event.target.value.split(",");
      this.router.navigate([],{
        relativeTo : this.route,
        queryParams : {order : order , orderType : orderType},
        queryParamsHandling : "merge"
      })
    this.fetchShelves();
  }

  protected readonly categoryImageLinkGenerator = categoryImageLinkGenerator;

}
