import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {HotToastService} from "@ngxpert/hot-toast";
import {AuthService} from "../services/auth/auth.service";
import {catchError, throwError} from "rxjs";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    FormsModule,
    RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{
    loginForm !: FormGroup

    constructor(private fb : FormBuilder , private toaster : HotToastService , private authService : AuthService) {}
    ngOnInit(): void {
        this.loginForm = this.fb.group({
          username : new FormControl('',[Validators.required]),
          password : new FormControl('',[Validators.required])
        })
    }

  handleLoginForm() {
      this.authService.login(this.loginForm);
  }
}
