import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WishlistRoutingModule } from './wishlist-routing-module';
import { Wishlist } from './wishlist/wishlist';

@NgModule({
  declarations: [
    Wishlist
  ],
  imports: [
    CommonModule,
    WishlistRoutingModule
  ]
})
export class WishlistModule { }
