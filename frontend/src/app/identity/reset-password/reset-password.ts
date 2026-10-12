import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ResetPasswordDto } from '../../shared/Models/ResetPassword';
import { Identity } from '../identity';

@Component({
  selector: 'app-reset-password',
  standalone: false,
  styleUrl: './reset-password.scss',
  templateUrl: './reset-password.html',
})
export class ResetPassword implements OnInit {

  loginForm?: FormGroup;
  ResetValue = new ResetPasswordDto();

  constructor(
    private router: ActivatedRoute,
    private fb: FormBuilder,
    private _service: Identity,
    private route:Router
  ) {}

  ngOnInit(): void {
    this.FormValidation();

    this.router.queryParams.subscribe((param) => {
      this.ResetValue.email = param['email'];
      this.ResetValue.token = param['code'];
    });
  }

  FormValidation() {
    this.loginForm = this.fb.group({
      password: ['', [Validators.required, Validators.pattern(/^(?=.*[0-9])(?=.*[#$@!.\-])[A-Za-z\d#$@!.\-]{8,}$/)]],
      confirmPassword: ['', [Validators.required, Validators.pattern(/^(?=.*[0-9])(?=.*[#$@!.\-])[A-Za-z\d#$@!.\-]{8,}$/)]]
    }, {
      validators: this.passwordMatchValidator // الـ Validator على مستوى الـ Group
    });
  }


  passwordMatchValidator(group: FormGroup) {
    const password = group.get('password')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;

    return password === confirmPassword ? null : { passwordMisMatch: true };
  }

  get _password() {
    return this.loginForm?.get('password');
  }

  get _confirmPassword() {
    return this.loginForm?.get('confirmPassword');
  }

  onSubmit(){
  if(this.loginForm?.valid){
    this.ResetValue.password = this.loginForm.value.password
    this._service.Resetpassword(this.ResetValue).subscribe({
      next: () => {
        this.route.navigateByUrl('/Account/Login')
      }, error(err) {
        console.log();
      }
    })
  }
}
}
