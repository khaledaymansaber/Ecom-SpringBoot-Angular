import { Component, Input } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { IProduct } from '../../shared/Models/Product';
import { BasketService } from '../../basket/basket';
import { WishlistService } from '../../core/Services/wishlist.service';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-shop-item',
  standalone: false,
  styleUrl: './shop-item.scss',
  templateUrl: './shop-item.html',
})
export class ShopItem {
  @Input() product!: IProduct;

  constructor(
    private _service: BasketService,
    private wishlistService: WishlistService,
    private toastr: ToastrService
  ) {}

  SetBasketValue() {
    this._service.addItemToBasket(this.product);
    this.toastr.success(`${this.product.name} added to cart`, 'Success');
  }

  addToWishlist() {
    this.wishlistService.addToWishlist(this.product.id).subscribe({
      next: () => {
        this.toastr.success(`${this.product.name} added to wishlist!`, 'Wishlist');
      },
      error: err => console.error(err)
    });
  }

  getImageUrl(imageName: string): string {
    if (!imageName) return './favicon.ico';
    if (imageName.startsWith('http')) return imageName;
    const cleanPath = imageName.startsWith('/') ? imageName.substring(1) : imageName;
    return environment.imageBaseURL + encodeURI(cleanPath);
  }
}
