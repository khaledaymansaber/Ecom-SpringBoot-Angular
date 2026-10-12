import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ShopService } from '../shop';
import { IProduct } from '../../shared/Models/Product';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BasketService } from '../../basket/basket';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-product-details',
  standalone: false,
  styleUrl: './product-details.scss',
  templateUrl: './product-details.html',
})
export class ProductDetails implements OnInit {

  product!: IProduct;
  qunatity: number = 1;
  MainImage!: string;

  constructor(
    private shopService: ShopService,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef,
    private toast: ToastrService,
    private basketService: BasketService
  ) {}

  ngOnInit(): void {
    this.loadProduct();
  }

  loadProduct() {
    const id = this.route.snapshot.paramMap.get('id')!;

    this.shopService.getProductDetails(parseInt(id)).subscribe({
      next: (value: IProduct) => {
        this.product = value;
        this.MainImage = this.product.photos?.[0]?.imageName;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  ReplaceImage(src: string) {
    this.MainImage = src;
  }

  getImageUrl(imageName: string | undefined): string {
    if (!imageName) return './favicon.ico';
    if (imageName.startsWith('http')) {
      return imageName;
    }
    const cleanPath = imageName.startsWith('/') ? imageName.substring(1) : imageName;
    return `${environment.imageBaseURL}${encodeURI(cleanPath)}`;
  }

  incrementBasket() {
    if (this.qunatity < 10) {
      this.qunatity++;
      this.toast.success('item has been added to the basket', 'SUCCESS');
    } else {
      this.toast.warning("You can't add more than 10 items", 'Enough');
    }
  }

  DecrementBasket() {
    if (this.qunatity > 1) {
      this.qunatity--;
      this.toast.warning('item has been Decrement', 'SUCCESS');
    } else {
      this.toast.error("You can't Decrement more than 1 items", 'ERROR');
    }
  }

  AddToBasket() {
    this.basketService.addItemToBasket(this.product, this.qunatity);
  }

  CalucateDiscount(oldPrice: number, newPrice: number): number {
    return parseFloat(
      Math.round(((oldPrice - newPrice) / oldPrice) * 100).toFixed(1)
    );
  }
}
