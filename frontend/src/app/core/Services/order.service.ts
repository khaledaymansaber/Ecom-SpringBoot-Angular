import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { IDeliveryMethod, IOrder, IOrderToCreate } from '../../shared/Models/Order';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  baseURL = environment.baseURL;

  constructor(private http: HttpClient) {}

  getDeliveryMethods() {
    return this.http.get<IDeliveryMethod[]>(this.baseURL + 'Orders/delivery-methods');
  }

  createOrder(orderToCreate: IOrderToCreate) {
    return this.http.post<IOrder>(this.baseURL + 'Orders/create', orderToCreate);
  }

  getOrdersForUser() {
    return this.http.get<IOrder[]>(this.baseURL + 'Orders');
  }

  getOrderById(id: number) {
    return this.http.get<IOrder>(this.baseURL + 'Orders/' + id);
  }
}
