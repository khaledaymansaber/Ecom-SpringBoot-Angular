import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Shop } from './shop/shop';
import { ShopItem } from './shop-item/shop-item';
import { SharedModule } from '../shared/shared-module';
import { ProductDetails } from './product-details/product-details';
import { RouterModule } from '@angular/router';
import { NgxImageZoomModule } from 'ngx-image-zoom';
import { ShopRoutingModule } from './shop-routing-module';

@NgModule({
  imports: [CommonModule,ShopRoutingModule, SharedModule,RouterModule,NgxImageZoomModule],
  declarations: [Shop, ShopItem, ProductDetails],
  exports: [],
})
export class ShopModule {}
