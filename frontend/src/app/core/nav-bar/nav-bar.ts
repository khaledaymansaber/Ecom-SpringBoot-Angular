import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BasketService } from '../../basket/basket';
import { WishlistService } from '../Services/wishlist.service';
import { Identity } from '../../identity/identity';

@Component({
  selector: 'app-nav-bar',
  standalone: false,
  styleUrl: './nav-bar.scss',
  templateUrl: './nav-bar.html',
})
export class NavBar implements OnInit {
  isDropdownOpen = false;
  count: number = 0;
  wishlistCount: number = 0;
  currentUser: any = null;

  constructor(
    private basketService: BasketService,
    private wishlistService: WishlistService,
    private identityService: Identity,
    private router: Router,
    private toastr: ToastrService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const basketId = localStorage.getItem('basket_id');
    if (basketId) {
      this.basketService.getBasket(basketId).subscribe({
        next: () => {},
        error: () => {}
      });
    }

    this.basketService.basket$.subscribe(basket => {
      this.count = (basket && basket.items) ? basket.items.length : 0;
      this.cdr.detectChanges();
    });

    this.wishlistService.wishlistCount$.subscribe(count => {
      this.wishlistCount = count;
      this.cdr.detectChanges();
    });

    this.identityService.currentUser$.subscribe(user => {
      this.currentUser = user;
      if (user) {
        this.wishlistService.getWishlist().subscribe();
      }
      this.cdr.detectChanges();
    });
  }

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  closeDropdown() {
    this.isDropdownOpen = false;
  }

  logout() {
    this.identityService.logout().subscribe({
      next: () => {
        this.isDropdownOpen = false;
        this.toastr.success('You have logged out', 'Logged Out');
        this.router.navigateByUrl('/');
      },
      error: () => {
        this.isDropdownOpen = false;
        this.router.navigateByUrl('/');
      }
    });
  }
}
