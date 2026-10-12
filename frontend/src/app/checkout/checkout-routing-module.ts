import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CheckoutModule } from './checkout-module';
import { Checkout } from './checkout/checkout';

const routes: Routes = [
  {path:'',component:Checkout}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CheckoutRoutingModule {}
