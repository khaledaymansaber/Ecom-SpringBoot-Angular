import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BasketService } from '../../basket/basket';
import { OrderService } from '../../core/Services/order.service';
import { ProfileService } from '../../core/Services/profile.service';
import { IBasket } from '../../shared/Models/Basket';
import { IDeliveryMethod, IOrderToCreate } from '../../shared/Models/Order';

@Component({
  selector: 'app-checkout',
  standalone: false,
  styleUrl: './checkout.scss',
  templateUrl: './checkout.html',
})
export class Checkout implements OnInit {
  checkoutForm!: FormGroup;
  deliveryMethods: IDeliveryMethod[] = [];
  selectedDeliveryMethod: IDeliveryMethod | null = null;
  basket: IBasket | null = null;
  loading = false;
  private orderPlaced = false;

  constructor(
    private fb: FormBuilder,
    private basketService: BasketService,
    private orderService: OrderService,
    private profileService: ProfileService,
    private toastr: ToastrService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.createCheckoutForm();
    this.loadBasket();
    this.loadDeliveryMethods();
    this.loadSavedAddress();
  }

  createCheckoutForm() {
    this.checkoutForm = this.fb.group({
      addressForm: this.fb.group({
        firstName: ['', [Validators.required, Validators.minLength(2)]],
        lastName: ['', [Validators.required, Validators.minLength(2)]],
        street: ['', Validators.required],
        city: ['', Validators.required],
        state: ['', Validators.required],
        zipCode: ['', [Validators.required, Validators.pattern(/^[0-9A-Za-z -]{3,10}$/)]]
      }),
      deliveryForm: this.fb.group({
        deliveryMethodId: ['', Validators.required]
      })
    });
  }

  loadBasket() {
    this.basketService.basket$.subscribe(basket => {
      this.basket = basket;
      if (!this.orderPlaced && (!basket || !basket.items || basket.items.length === 0)) {
        this.toastr.info('Your basket is empty');
        this.router.navigateByUrl('/shop');
      }
      this.cdr.detectChanges();
    });
  }

  loadDeliveryMethods() {
    this.orderService.getDeliveryMethods().subscribe({
      next: methods => {
        this.deliveryMethods = methods;
        if (methods.length > 0) {
          this.selectDeliveryMethod(methods[0]);
        }
        this.cdr.detectChanges();
      },
      error: err => {
        console.error('Error fetching delivery methods', err);
        this.cdr.detectChanges();
      }
    });
  }

  loadSavedAddress() {
    this.profileService.getAddress().subscribe({
      next: address => {
        if (address) {
          this.checkoutForm.get('addressForm')?.patchValue(address);
          this.cdr.detectChanges();
        }
      },
      error: () => {
        // Address not yet set, user will fill manually
      }
    });
  }

  selectDeliveryMethod(method: IDeliveryMethod) {
    this.selectedDeliveryMethod = method;
    this.checkoutForm.get('deliveryForm.deliveryMethodId')?.setValue(method.id);
  }

  getSubtotal(): number {
    if (!this.basket || !this.basket.items) return 0;
    return this.basket.items.reduce((acc, item) => acc + (item.price * item.quantity), 0);
  }

  getShippingCost(): number {
    return this.selectedDeliveryMethod ? this.selectedDeliveryMethod.price : 0;
  }

  getTotal(): number {
    return this.getSubtotal() + this.getShippingCost();
  }

  getImageUrl(imagePath: string | undefined): string {
    if (!imagePath) return './favicon.ico';
    const trimmed = imagePath.trim();
    if (trimmed.startsWith('http://') || trimmed.startsWith('https://')) {
      return trimmed;
    }
    const cleanPath = trimmed.startsWith('/') ? trimmed.substring(1) : trimmed;
    return 'http://localhost:8080/images/' + encodeURI(cleanPath);
  }

  trackById(index: number, item: any): any {
    return item?.id || index;
  }

  submitOrder() {
    if (this.checkoutForm.invalid || !this.basket) {
      this.toastr.error('Please complete all required fields', 'Invalid Order');
      this.checkoutForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.orderPlaced = true;
    const address = this.checkoutForm.get('addressForm')?.value;
    const deliveryMethodId = this.checkoutForm.get('deliveryForm.deliveryMethodId')?.value;

    const orderToCreate: IOrderToCreate = {
      basketId: this.basket.id,
      deliveryMethodId: Number(deliveryMethodId),
      shippingAddress: address
    };

    this.orderService.createOrder(orderToCreate).subscribe({
      next: order => {
        this.loading = false;
        this.toastr.success('Order placed successfully! Order #' + order.id, 'Success');
        this.basketService.clearLocalBasket();
        this.router.navigate(['/orders', order.id]);
      },
      error: err => {
        this.loading = false;
        this.orderPlaced = false;
        console.error('Order creation failed', err);
        this.toastr.error('Could not create order. Please try again.', 'Order Error');
        this.cdr.detectChanges();
      }
    });
  }
}
