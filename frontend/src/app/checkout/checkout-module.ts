import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { CheckoutRoutingModule } from './checkout-routing-module';
import { Checkout } from './checkout/checkout';

@NgModule({
  declarations: [Checkout],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    CheckoutRoutingModule
  ],
})
export class CheckoutModule {}
