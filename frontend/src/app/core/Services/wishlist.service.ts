import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { IWishlistItem } from '../../shared/Models/Wishlist';

@Injectable({
  providedIn: 'root'
})
export class WishlistService {
  baseURL = environment.baseURL;

  private wishlistCountSource = new BehaviorSubject<number>(0);
  wishlistCount$ = this.wishlistCountSource.asObservable();

  constructor(private http: HttpClient) {}

  getWishlist() {
    return this.http.get<IWishlistItem[]>(this.baseURL + 'Wishlist').pipe(
      map(items => {
        this.wishlistCountSource.next(items ? items.length : 0);
        return items;
      })
    );
  }

  addToWishlist(productId: number) {
    return this.http.post<IWishlistItem>(this.baseURL + 'Wishlist/' + productId, {}).pipe(
      map(item => {
        this.wishlistCountSource.next(this.wishlistCountSource.value + 1);
        return item;
      })
    );
  }

  removeFromWishlist(productId: number) {
    return this.http.delete(this.baseURL + 'Wishlist/' + productId).pipe(
      map(res => {
        const count = Math.max(0, this.wishlistCountSource.value - 1);
        this.wishlistCountSource.next(count);
        return res;
      })
    );
  }

  checkInWishlist(productId: number) {
    return this.http.get<{ inWishlist: boolean }>(this.baseURL + 'Wishlist/check/' + productId);
  }
}
