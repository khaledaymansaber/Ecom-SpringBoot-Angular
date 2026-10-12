import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { IShippingAddress } from '../../shared/Models/Order';
import { IUpdateProfile, IUserProfile } from '../../shared/Models/UserProfile';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  baseURL = environment.baseURL;

  constructor(private http: HttpClient) {}

  getProfile() {
    return this.http.get<IUserProfile>(this.baseURL + 'Profile');
  }

  updateProfile(profile: IUpdateProfile) {
    return this.http.put<IUserProfile>(this.baseURL + 'Profile', profile);
  }

  getAddress() {
    return this.http.get<IShippingAddress>(this.baseURL + 'Profile/address');
  }

  updateAddress(address: IShippingAddress) {
    return this.http.put<IShippingAddress>(this.baseURL + 'Profile/address', address);
  }
}
