import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PaginationComponent, PaginationModule } from 'ngx-bootstrap/pagination';
import { Pagnation } from './Component/pagnation/pagnation';
import { RouterModule } from '@angular/router';
import { OrderTotal } from './Component/order-total/order-total';

@NgModule({
  declarations: [Pagnation, OrderTotal],
  imports: [CommonModule, PaginationModule, RouterModule],
  exports: [PaginationModule, Pagnation,OrderTotal],
})
export class SharedModule {}
