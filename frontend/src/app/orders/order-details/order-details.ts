import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderService } from '../../core/Services/order.service';
import { IOrder } from '../../shared/Models/Order';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-order-details',
  standalone: false,
  styleUrl: './order-details.scss',
  templateUrl: './order-details.html',
})
export class OrderDetails implements OnInit {
  order: IOrder | null = null;
  loading = true;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private orderService: OrderService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadOrder(+id);
    } else {
      this.loading = false;
      this.errorMessage = 'Invalid order ID';
      this.cdr.detectChanges();
    }
  }

  loadOrder(id: number) {
    this.loading = true;
    this.errorMessage = '';
    this.orderService.getOrderById(id).subscribe({
      next: order => {
        this.order = order;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: err => {
        console.error('Error fetching order', err);
        this.loading = false;
        this.errorMessage = 'Could not load order details. Please check your order history.';
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
}
