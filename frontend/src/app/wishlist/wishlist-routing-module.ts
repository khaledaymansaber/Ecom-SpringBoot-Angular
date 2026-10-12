import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Wishlist } from './wishlist/wishlist';

const routes: Routes = [
  { path: '', component: Wishlist }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WishlistRoutingModule { }
