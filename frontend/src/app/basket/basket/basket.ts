import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { BasketService } from '../basket';
import { IBasket, IBasketItem, IBasketTotal } from '../../shared/Models/Basket';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-basket',
  standalone: false,
  styleUrl: './basket.scss',
  templateUrl: './basket.html',
})
export class Basket implements OnInit {

  basket: IBasket | null = null;
  basketTotals$!: Observable<IBasketTotal | null>;

  constructor(
    private _service: BasketService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.basketTotals$ = this._service.basketTotal$;

    this._service.basket$.subscribe({
      next: (value) => {
        this.basket = value;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.log(err);
        this.cdr.detectChanges();
      }
    });
  }

  getImageUrl(imagePath: string | undefined): string {
    if (!imagePath) return './favicon.ico';
    const trimmed = imagePath.trim();
    if (trimmed.startsWith('http://') || trimmed.startsWith('https://')) {
      return trimmed;
    }
    const cleanPath = trimmed.startsWith('/') ? trimmed.substring(1) : trimmed;
    return environment.imageBaseURL + encodeURI(cleanPath);
  }

  trackById(index: number, item: any): any {
    return item?.id || index;
  }

  incrementQuantity(item: IBasketItem) {
    this._service.incrementBasketItemQuantity(item);
  }

  decrementQuantity(item: IBasketItem) {
    this._service.DecrementBasketItemQuantity(item);
  }

  removeItem(item: IBasketItem) {
    this._service.removeItemFormBasket(item);
  }
}
