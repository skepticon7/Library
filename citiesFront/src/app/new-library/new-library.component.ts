import {Component, OnInit} from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {Library} from "../models/library.model";
import {LibrariesService} from "../services/libraries/libraries.service";
import {ActivatedRoute} from "@angular/router";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-new-library',
  standalone: true,
    imports: [
        NavbarComponent,
        FormsModule,
        ReactiveFormsModule,
        NgIf
    ],
  templateUrl: './new-library.component.html',
  styleUrl: './new-library.component.css'
})
export class NewLibraryComponent implements OnInit{
  newLibraryForm! : FormGroup;
  data! : Library;
  cityId! : string;

  constructor(private libraryService : LibrariesService , private fb : FormBuilder , private route : ActivatedRoute) {}

  ngOnInit(): void {
    this.newLibraryForm = this.fb.group({
      name : [null,Validators.required],
      shelvesCapacity : [null,Validators.required],
      visitorsCapacity : [null , Validators.required]
    })
    this.route.queryParams.subscribe(params =>{
      this.cityId = params['cityId'];
    })
  }


  handleNewLibrary() {
    let newLibraryData : Library = this.newLibraryForm.value;
    if(this.newLibraryForm.invalid){
      alert("please fill in the fields");
      return;
    }
    this.libraryService.saveLibrary(this.cityId,newLibraryData).subscribe({
      next : data => {
        alert("successfully added library to city");
        this.data = data;
      },
      error : err => {
        alert("error saving library to the city" );
        console.log("console error adding the library : " + err.error.message);
      }
    })
  }
}
