import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
// استدعاء المكون من نفس الفولدر الحالي
import { Basket } from './basket/basket';
const routes: Routes = [
  { path: '', component: Basket } // استخدام اسم الكلاس الصحيح
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class BasketRoutingModule {}
