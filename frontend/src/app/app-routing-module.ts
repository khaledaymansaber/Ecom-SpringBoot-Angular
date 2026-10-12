import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Home } from './home/home';
import { authGuard } from './core/guards/auth.guard';

const routes: Routes = [
  { path: '', component: Home },
  {
    path: 'shop',
    loadChildren: () => import('./shop/shop-module').then(m => m.ShopModule)
  },
  {
    path: 'basket',
    loadChildren: () => import('./basket/basket-module').then(m => m.BasketModule)
  },
  {
    path: 'checkout',
    canActivate: [authGuard],
    loadChildren: () => import('./checkout/checkout-module').then(m => m.CheckoutModule)
  },
  {
    path: 'Account',
    loadChildren: () => import('./identity/identity-module').then(m => m.IdentityModule)
  },
  {
    path: 'orders',
    canActivate: [authGuard],
    loadChildren: () => import('./orders/orders-module').then(m => m.OrdersModule)
  },
  {
    path: 'wishlist',
    canActivate: [authGuard],
    loadChildren: () => import('./wishlist/wishlist-module').then(m => m.WishlistModule)
  },
  {
    path: 'profile',
    canActivate: [authGuard],
    loadChildren: () => import('./profile/profile-module').then(m => m.ProfileModule)
  },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
