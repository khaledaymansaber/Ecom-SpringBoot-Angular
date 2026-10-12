import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { BasketService } from '../basket/basket';
import { WishlistService } from '../core/Services/wishlist.service';
import { ICategory } from '../shared/Models/Category';
import { IProduct } from '../shared/Models/Product';
import { ProductParams } from '../shared/Models/ProductParams';
import { ShopService } from '../shop/shop';

@Component({
  selector: 'app-home',
  standalone: false,
  styleUrl: './home.scss',
  templateUrl: './home.html',
})
export class Home implements OnInit {
  featuredProducts: IProduct[] = [];
  categories: ICategory[] = [];
  loading = true;

  constructor(
    private shopService: ShopService,
    private basketService: BasketService,
    private wishlistService: WishlistService,
    private toastr: ToastrService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadFeaturedProducts();
    this.loadCategories();
  }

  loadFeaturedProducts(): void {
    const params = new ProductParams();
    params.pageSize = 4;
    params.pageNumber = 1;

    this.shopService.getProduct(params).subscribe({
      next: res => {
        this.featuredProducts = (res && res.data) ? res.data : [];
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: err => {
        console.error('Error loading featured products', err);
        this.featuredProducts = [];
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  loadCategories(): void {
    this.shopService.getCategory().subscribe({
      next: cats => {
        this.categories = cats || [];
        this.cdr.detectChanges();
      },
      error: err => {
        console.error('Error loading categories', err);
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
    return `http://localhost:8080/images/${encodeURI(name)}`;
  }

  addToCart(product: IProduct): void {
    this.basketService.addItemToBasket(product);
    this.toastr.success(`${product.name} added to your basket!`, 'Added to Cart');
  }

  addToWishlist(product: IProduct): void {
    this.wishlistService.addToWishlist(product.id).subscribe({
      next: () => {
        this.toastr.success(`${product.name} added to your wishlist!`, 'Wishlist');
      },
      error: err => {
        console.error('Wishlist error', err);
      }
    });
  }

  trackById(index: number, item: any): any {
    return item?.id || index;
  }
}
