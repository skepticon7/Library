import {Component, OnInit} from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {ShelfService} from "../services/shelf/shelf.service";
import {ActivatedRoute, Route, Router} from "@angular/router";
import {Observable} from "rxjs";
import {Shelf} from "../models/shelf.model";
import {NgIf} from "@angular/common";

// @ts-ignore
@Component({
  selector: 'app-new-shelf',
  standalone: true,
    imports: [
        NavbarComponent,
        FormsModule,
        ReactiveFormsModule,
        NgIf
    ],
  templateUrl: './new-shelf.component.html',
  styleUrl: './new-shelf.component.css'
})
export class NewShelfComponent implements OnInit{
  newShelfForm! : FormGroup;
  libraryId !: string;
  data !: Shelf

  constructor(private fb : FormBuilder , private shelfService : ShelfService , private route : ActivatedRoute , private router : Router) {}

  ngOnInit(): void {
    this.newShelfForm = this.fb.group({
      shelfNumber : ['' , Validators.required],
      booksCapacity : [null , Validators.required],
      category : ['' , Validators.required]
    })
    this.route.queryParams.subscribe(params => {
      this.libraryId = params["libraryId"];
    })
  }

  handleNewShelf() {
    let newShelfData = this.newShelfForm.value;
    if(newShelfData.invalid){
      alert("please fill in the fields");
      return;
    }
    this.shelfService.saveShelf(this.libraryId , newShelfData).subscribe({
      next : data =>{
        alert("shelf successfully added");
        this.data = data;
      },
      error : err => {
        alert("error saving shelf in the library");
        console.log("error in console saving shelf : " + err.error.message);
      }
    })
  }
}
