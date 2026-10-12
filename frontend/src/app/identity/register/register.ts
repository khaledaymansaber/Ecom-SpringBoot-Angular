import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Identity } from '../identity';
import { ToastrService } from 'ngx-toastr';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: false,
  styleUrl: './register.scss',
  templateUrl: './register.html',
})
export class Register implements OnInit {
  fromGroup?: FormGroup;

  constructor(
    private fb: FormBuilder,
    private _service: Identity,
    private toast: ToastrService,
    private route: Router
  ) {}

  ngOnInit(): void {
    this.formValidation();
  }

  formValidation() {
    this.fromGroup = this.fb.group({
      UserName: ['', [Validators.required, Validators.minLength(6)]],
      email: ['', [Validators.required, Validators.email]],
      DisplayName: ['', [Validators.required]],
      password: [
        '',
        [
          Validators.required,
          Validators.pattern(
            /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[#$@!%*?&.]).{8,}$/
          ),
        ],
      ],
    });
  }

  get _username() {
    return this.fromGroup?.get('UserName');
  }

  get _email() {
    return this.fromGroup?.get('email');
  }

  get _DisplayName() {
    return this.fromGroup?.get('DisplayName');
  }

  get _Password() {
    return this.fromGroup?.get('password');
  }

  Submit() {
    if (this.fromGroup?.valid) {
      this._service.register(this.fromGroup.value).subscribe({

        next: (value) => {
          console.log(value);

          this.toast.success(
            "Register success, please confirm your email",
            "success".toUpperCase()
          );
          this.route.navigateByUrl('/Account/Login')
        },
        error: (err) => {
          console.log(err);

          this.toast.error(
            err.error.message,
            "error".toUpperCase()
          );
        }
      });
    }
  }
}

