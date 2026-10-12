import { Component, OnInit } from '@angular/core';
import { BasketService } from '../basket';
import { IBasket, IBasketItem, IBasketTotal } from '../../shared/Models/Basket';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-basket',
  standalone: false,
  styleUrl: './basket.scss',
  templateUrl: './basket.html',
})
export class Basket implements OnInit {

  basket: IBasket | null = null;
  basketTotals$!: Observable<IBasketTotal | null>;

  constructor(private _service: BasketService){}

  ngOnInit(): void {
    this.basketTotals$ = this._service.basketTotal$;

    this._service.basket$.subscribe({
      next: (value) => {
        this.basket = value;
      },
      error: (err) => {
        console.log(err);
      }
    });
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
