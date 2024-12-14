import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {RouterLink} from "@angular/router";
import {AuthService} from "../services/auth/auth.service";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-signup',
  standalone: true,
    imports: [
        FormsModule,
        ReactiveFormsModule,
        RouterLink
    ],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent implements OnInit{
  signupForm !: FormGroup;
  constructor(private fb : FormBuilder , private authService : AuthService , private datePipe : DatePipe) {}

  ngOnInit(): void {
       this.signupForm = this.fb.group({
         name : new FormControl('' , [Validators.required]),
         surname : new FormControl('' , [Validators.required]),
         password : new FormControl('',[Validators.required ]),
         email : new FormControl('',[Validators.required , Validators.email]),
         birthDate : new FormControl('',[Validators.required]),
         gender : new FormControl('Male',[Validators.required]),
         role : new FormControl('Visitor',[Validators.required])
       })
  }

  handleSignupForm() {
    const rawBirthDate = this.signupForm.value["birthDate"];
    const transformedDate = this.datePipe.transform(rawBirthDate, "yyyy-MM-dd");
    if (transformedDate) {
      this.signupForm.patchValue({
        birthDate: transformedDate
      });
    }

    this.authService.signup(this.signupForm);
  }
}
