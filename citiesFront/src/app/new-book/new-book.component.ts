import {Component, OnInit} from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {BookService} from "../services/Book/book.service";
import {CloudinaryService} from "../services/cloudinary/cloudinary.service";
import {book} from "../models/book.mode";
import {catchError, Observable, throwError} from "rxjs";
import { HotToastService } from '@ngxpert/hot-toast';
import {NgIf} from "@angular/common";
import {yearRangeValidator , priceValidator} from "../utils/customValidator.utils";

@Component({
  selector: 'app-new-book',
  standalone: true,
  imports: [
    NavbarComponent,
    ReactiveFormsModule,
    NgIf,

  ],
  templateUrl: './new-book.component.html',
  styleUrl: './new-book.component.css'
})
export class NewBookComponent implements OnInit{
  newBookForm !: FormGroup;
  currentYear!: number ;
  selectedImage !: File;
  shelfId !: string;
  data !: book;
  selectedFile !: File;

  constructor(private toaster : HotToastService,private router : Router , private route : ActivatedRoute , private bookService : BookService , private fb : FormBuilder , private imageService : CloudinaryService) {}

  ngOnInit(): void {
        this.route.queryParams.subscribe(params => {
          this.shelfId = params["shelfId"];
        })
        this.currentYear = new Date().getFullYear();
        this.newBookForm = this.fb.group({
          title : new FormControl('',[Validators.required]),
          author : new FormControl('',[Validators.required]),
          publisher : new FormControl('',[Validators.required]),
          description : new FormControl('',[Validators.required , Validators.minLength(5),Validators.maxLength(255)]),
          yearEra: this.fb.group({
            year: ['', Validators.required],
            era: ['CE', Validators.required]
          }, { validators: yearRangeValidator() }),
          pages : new FormControl('',[Validators.required]),
          copies : new FormControl('',[Validators.required]),
          cover : new FormControl('',[Validators.required]),
          pdf : new FormControl('',[Validators.required]),
          price : this.fb.group({
            halfPrice : new FormControl(null,[Validators.required]),
            onePrice : new FormControl(null,[Validators.required]),
            oneHalfPrice : new FormControl(null,[Validators.required]),
          },{validators : priceValidator()})
        });
  }

  handleNewBook() {
    let newBookFormData = {
      ...this.newBookForm.value,
      ...this.newBookForm.value.yearEra,
      ...this.newBookForm.value.price
    };
    delete newBookFormData.yearEra;
    delete newBookFormData.price;
    let signature : Observable<any> = this.imageService.getSignature();
    if(signature){
      signature.subscribe(async ({signature, timestamp}) => {
        try {
          let [imageResponse, fileResponse] = await Promise.all([this.imageService.uploadImage(this.selectedImage, signature, timestamp) , this.imageService.uploadFile(this.selectedFile, signature, timestamp)]);
          imageResponse.subscribe(response => {
            console.log("image : " + response.secure_url);
            newBookFormData.cover = response.secure_url;
          })
          fileResponse.subscribe(response => {
            console.log("pdf file : " + response.secure_url);
            newBookFormData.pdf = response.secure_url;
          })
        }catch (err){
          console.log("error fetching files to cloud" , err);
          throw new Error("error fetching files to cloud");
        }
// /*          this.imageService.uploadImage(this.selectedImage, signature, timestamp).pipe(
//             this.toaster.observe({
//               loading: "Saving cover to the cloud...",
//               success: "Cover saved",
//               error: "Could not save"
//             })
//           ).subscribe(response => {
//             newBookFormData.cover = response.secure_url;
//             this.bookService.saveBook(this.shelfId, newBookFormData).pipe(
//               this.toaster.observe({
//                 loading: "Saving book...",
//                 success: "Book saved",
//                 error: (err) => {
//                   return (err as { error: { message: string } }).error.message || "Could not save";
//                 }
//               }),
//               catchError(err => {
//                 return this.imageService.deleteImage(response.public_id).pipe(
//                   this.toaster.observe({
//                     loading: "Deleting cover...",
//                     success: "Cover deleted from cloud",
//                     error: "Could not delete cover"
//                   })
//                 );
//               })
//             ).subscribe(data => {
//               this.data = data;
//             })
//           })*/
      })
    }else{
      this.toaster.error("couldnt find signature lil bro");
    }
  }

  handleCoverChange(event : any) {
      let image = event.target.files[0];
      if(image)
        this.selectedImage = image;
  }

  handleFileChange(event : any) {
    let file = event.target.files[0];
    if(file)
      this.selectedFile = file;
  }



}
