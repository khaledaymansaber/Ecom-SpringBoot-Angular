import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { OrderService } from '../../core/Services/order.service';
import { IOrder } from '../../shared/Models/Order';

@Component({
  selector: 'app-orders',
  standalone: false,
  styleUrl: './orders.scss',
  templateUrl: './orders.html',
})
export class Orders implements OnInit {
  orders: IOrder[] = [];
  loading = true;

  constructor(
    private orderService: OrderService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders() {
    this.loading = true;
    this.orderService.getOrdersForUser().subscribe({
      next: (orders: IOrder[]) => {
        this.orders = orders || [];
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        console.error('Error loading orders', err);
        this.orders = [];
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  trackById(index: number, order: any): any {
    return order?.id || index;
  }
}
