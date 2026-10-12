import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Shop } from './shop/shop';
import { ProductDetails } from './product-details/product-details';

const routes: Routes = [
  {
    path: "", // <--- تعديل مهم جداً: خليناها فاضية عشان تفتح على مسار /shop مباشرة
    component: Shop
  },
  {
    path: 'product-details/:id', // دي هتفتح كده: /shop/product-details/1
    component: ProductDetails
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ShopRoutingModule { }
