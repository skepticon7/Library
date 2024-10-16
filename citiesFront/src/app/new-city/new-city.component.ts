import {Component, OnInit} from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {City} from "../models/city.model";
import {CityService} from "../services/city/city.service";
import {CloudinaryService} from "../services/cloudinary/cloudinary.service";
import {data} from "autoprefixer";
import {NgIf} from "@angular/common";
import {HotToastService} from "@ngxpert/hot-toast";
import {Observable} from "rxjs";

@Component({
  selector: 'app-new-city',
  standalone: true,
  imports: [
    NavbarComponent,
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './new-city.component.html',
  styleUrl: './new-city.component.css'
})
export class NewCityComponent implements OnInit{

    newCityForm! : FormGroup;
    selectedFile! : File;
    data! : City;
    constructor(private toaster : HotToastService,private fb : FormBuilder , private cityService : CityService , private cloudinaryService : CloudinaryService) {
    }

    ngOnInit(): void {
        this.newCityForm = this.fb.group({
          name : [null,Validators.required],
          photo : [null,Validators.required]
        })
    }

  onFileChange(event : any) {
    const file = event.target.files[0];
    if(file)
      this.selectedFile = file;
  }

  handleNewCity() {
    let newCity : City = this.newCityForm.value;
    if(this.newCityForm.invalid){
      alert("please fill in the fields");
      return;
    }
    let signature : Observable<any> = this.cloudinaryService.getSignature();
    if(signature){
      signature.subscribe(({signature , timestamp}) => {
        this.cloudinaryService.uploadImage(this.selectedFile , signature , timestamp).subscribe({
          next : response =>{
            newCity.photo = response.secure_url;
            this.cityService.saveCity(newCity).subscribe({
              next : data =>{
                alert("city has been successfully added");
                this.data = data;
              },
              error : err => {
                alert("error adding the city");
                console.log("error adding city : " + err.error.message);
              }
            })
          }
        })
      })
    }else{
      this.toaster.error("no signature ever found lil bro");
    }



  }


}
