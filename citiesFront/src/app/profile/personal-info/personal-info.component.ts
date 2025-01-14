import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth/auth.service";
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {PersonalInfoService} from "../../services/personalInfo/personal-info.service";
import {DatePipe} from "@angular/common";
import {HotToastService} from "@ngxpert/hot-toast";
import {catchError, first, throwError} from "rxjs";

@Component({
  selector: 'app-personal-info',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './personal-info.component.html',
  styleUrl: './personal-info.component.css'
})
export class PersonalInfoComponent implements OnInit{
  personalInfoForm !: FormGroup;



  constructor(private toaster : HotToastService, private fb : FormBuilder,protected authService : AuthService , private personalInfoService : PersonalInfoService , private datePipe : DatePipe) {}

  ngOnInit() {
    this.personalInfoForm = this.fb.nonNullable.group({
      surname : new FormControl('', [Validators.required]),
      name : new FormControl('' , [Validators.required]),
      email : new FormControl('' , [Validators.required , Validators.email]),
      birthDate : new FormControl('' , [Validators.required])
    });
  }

  submitPersonalInfo() {
      const dateFormat : string | null = this.datePipe.transform(this.personalInfoForm.controls['birthDate'].value, "yyyy-MM-dd");
      if(dateFormat){
        this.toaster.error("")
        this.personalInfoForm.patchValue({
          birthDate : dateFormat
        });
      }

      if(this.personalInfoForm.valid){
        this.personalInfoService.updatePersonalInfo(this.authService.getCurrentUser()!.id , this.personalInfoForm)
          .pipe(
            first(),
            catchError(err => {
              console.log("error : " +  err);
              return throwError(() : Error => new Error(err.message));
            })
          ).subscribe(res => {
              this.toaster.success("Personal Info Updated");
              console.log(res);
          })
      }

  }
}
