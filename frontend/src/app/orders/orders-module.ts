import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrdersRoutingModule } from './orders-routing-module';
import { Orders } from './orders/orders';
import { OrderDetails } from './order-details/order-details';

@NgModule({
  declarations: [
    Orders,
    OrderDetails
  ],
  imports: [
    CommonModule,
    OrdersRoutingModule
  ]
})
export class OrdersModule { }
