import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Orders } from './orders/orders';
import { OrderDetails } from './order-details/order-details';

const routes: Routes = [
  { path: '', component: Orders },
  { path: ':id', component: OrderDetails }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrdersRoutingModule { }
