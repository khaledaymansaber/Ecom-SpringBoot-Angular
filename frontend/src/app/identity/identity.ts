import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map } from 'rxjs';
import { environment } from '../../environments/environment';
import { ActiveAccount } from '../shared/Models/ActiveAccount';
import { ResetPasswordDto } from '../shared/Models/ResetPassword';

@Injectable({
  providedIn: 'root'
})
export class Identity {

  baseURL = environment.baseURL;

  private currentUserSource = new BehaviorSubject<any>(null);
  currentUser$ = this.currentUserSource.asObservable();

  constructor(private http: HttpClient) {
    this.checkInitialAuthState();
  }

  private checkInitialAuthState() {
    const storedUser = localStorage.getItem('user_info');
    if (storedUser) {
      try {
        this.currentUserSource.next(JSON.parse(storedUser));
      } catch (e) {
        localStorage.removeItem('user_info');
      }
    }
  }

  register(form: any) {
    return this.http.post(this.baseURL + 'Account/Register', form);
  }

  active(param: ActiveAccount) {
    return this.http.post(this.baseURL + 'Account/Active-account', param);
  }

  Login(form: any) {
    return this.http.post<any>(this.baseURL + 'Account/Login', form).pipe(
      map(res => {
        if (res && res.token) {
          localStorage.setItem('token', res.token);
        }
        const userInfo = { email: form.email, loggedIn: true };
        localStorage.setItem('user_info', JSON.stringify(userInfo));
        this.currentUserSource.next(userInfo);
        return res;
      })
    );
  }

  logout() {
    return this.http.post(this.baseURL + 'Account/Logout', {}).pipe(
      map(res => {
        localStorage.removeItem('user_info');
        localStorage.removeItem('token');
        this.currentUserSource.next(null);
        return res;
      })
    );
  }

  forgetpasword(email: string) {
    return this.http.post(this.baseURL + `Account/SendEmail-ForForgetPassword?email=${email}`, {});
  }

  Resetpassword(param: ResetPasswordDto) {
    return this.http.post(this.baseURL + 'Account/Reset-Password', param);
  }

  getCurrentUserValue() {
    return this.currentUserSource.value;
  }
}
