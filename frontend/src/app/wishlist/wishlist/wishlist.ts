import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { BasketService } from '../../basket/basket';
import { WishlistService } from '../../core/Services/wishlist.service';
import { IWishlistItem } from '../../shared/Models/Wishlist';
import { IProduct } from '../../shared/Models/Product';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-wishlist',
  standalone: false,
  styleUrl: './wishlist.scss',
  templateUrl: './wishlist.html',
})
export class Wishlist implements OnInit {
  wishlistItems: IWishlistItem[] = [];
  loading = true;

  constructor(
    private wishlistService: WishlistService,
    private basketService: BasketService,
    private toastr: ToastrService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadWishlist();
  }

  loadWishlist() {
    this.loading = true;
    this.wishlistService.getWishlist().subscribe({
      next: (items: IWishlistItem[]) => {
        this.wishlistItems = items || [];
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        console.error('Error fetching wishlist', err);
        this.wishlistItems = [];
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  moveToCart(product: IProduct) {
    this.basketService.addItemToBasket(product);
    this.removeItem(product.id);
    this.toastr.success(`${product.name} moved to your basket!`, 'Success');
  }

  removeItem(productId: number) {
    this.wishlistService.removeFromWishlist(productId).subscribe({
      next: () => {
        this.wishlistItems = this.wishlistItems.filter(item => item.product?.id !== productId);
        this.toastr.info('Item removed from wishlist');
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        console.error('Failed to remove wishlist item', err);
        this.cdr.detectChanges();
      }
    });
  }

  getImageUrl(photos: any[] | undefined): string {
    if (!photos || photos.length === 0 || !photos[0]?.imageName) {
      return './favicon.ico';
    }
    const name = photos[0].imageName;
    if (name.startsWith('http')) return name;
    return `${environment.imageBaseURL}${encodeURI(name)}`;
  }

  trackById(index: number, item: any): any {
    return item?.product?.id || item?.id || index;
  }
}
