import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { IBasketTotal } from '../../Models/Basket';
import { BasketService } from '../../../basket/basket';

@Component({
  selector: 'app-order-total',
  standalone: false,
  styleUrl: './order-total.scss',
  templateUrl: './order-total.html',
})
export class OrderTotal implements OnInit {

  basketTotals$!: Observable<IBasketTotal | null>;

  constructor(private basketService: BasketService){}

  ngOnInit(): void {
    // ربط المكون مباشرة بالـ Stream
    this.basketTotals$ = this.basketService.basketTotal$;
  }
}
