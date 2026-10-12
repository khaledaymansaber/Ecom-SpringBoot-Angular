import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Identity } from '../identity';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: false,
  styleUrl: './login.scss',
  templateUrl: './login.html',
})
export class Login implements OnInit {
  loginForm?: FormGroup;
  emailModel:string ='';

  constructor(private fb: FormBuilder,private _service:Identity,private route:Router ) {}
  ngOnInit(): void {
    this.FormValidation()
  }
  FormValidation(){
    this.loginForm = this.fb.group({
      email: ['', [
        Validators.required,
        Validators.email
      ]],
      password: ['', [
        Validators.required,
        Validators.pattern(
            /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[#$@!%*?&.]).{8,}$/
          ),
      ]]
    });
  }
  get _email() {
    return this.loginForm?.get('email');
  }

  get _Password() {
    return this.loginForm?.get('password');
  }
  onSubmit() {
        if (this.loginForm?.valid) {
    this._service.Login(this.loginForm?.value).subscribe({
        next:(value) => {
          console.log(value);
          this.route.navigateByUrl('/')
        },
        error: (err) => {
          console.log(err);
        }
      });

    }
  }
  SendEmailForgetpassword(){
    this._service.forgetpasword(this.emailModel).subscribe({
        next:(value) => {
          console.log(value);
        },
        error: (err) => {
          console.log(err);
        }
      });
  }
}
