import { Component, Input } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { IProduct } from '../../shared/Models/Product';
import { BasketService } from '../../basket/basket';
import { WishlistService } from '../../core/Services/wishlist.service';

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
    if (imageName.startsWith('/')) return 'http://localhost:8080' + imageName;
    return 'http://localhost:8080/images/' + encodeURI(imageName);
  }
}
