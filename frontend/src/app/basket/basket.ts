import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map } from 'rxjs';
import { IBasket, IBasketItem, Basket, IBasketTotal } from '../shared/Models/Basket';
import { IProduct } from '../shared/Models/Product';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BasketService {

  baseUrl = environment.baseURL;

  private basketSource = new BehaviorSubject<IBasket | null>(null);
  basket$ = this.basketSource.asObservable();

  private basketSourceTotal = new BehaviorSubject<IBasketTotal | null>(null);
  basketTotal$ = this.basketSourceTotal.asObservable();

  constructor(private http: HttpClient) {
    const basketId = localStorage.getItem('basket_id');
    if (basketId) {
      this.getBasket(basketId).subscribe({
        next: () => {},
        error: () => this.clearLocalBasket()
      });
    }
  }

  calculateTotal() {
    const basket = this.getCurrentBasketValue();

    if (!basket || !basket.items || basket.items.length === 0) {
      this.basketSourceTotal.next(null);
      return;
    }

    const shipping = 0;
    const subtotal = basket.items.reduce((a, c) => (c.price * c.quantity) + a, 0);
    const total = shipping + subtotal;

    this.basketSourceTotal.next({ shipping, subtotal, total });
  }

  getBasket(id: string) {
    return this.http.get<IBasket>(this.baseUrl + 'Basket/' + id).pipe(
      map((basket: IBasket) => {
        this.basketSource.next(basket);
        this.calculateTotal();
        return basket;
      })
    );
  }

  setBasket(basket: IBasket) {
    return this.http.post<IBasket>(this.baseUrl + 'Basket', basket).pipe(
      map((response: IBasket) => {
        this.basketSource.next(response);
        this.calculateTotal();
        return response;
      })
    );
  }

  getCurrentBasketValue() {
    return this.basketSource.value;
  }

  addItemToBasket(product: IProduct, quantity: number = 1) {
    const itemToAdd = this.mapProductItemToBasketItem(product, quantity);
    const basket = this.getCurrentBasketValue() ?? this.createBasket();

    basket.items = this.addOrUpdateItem(basket.items, itemToAdd, quantity);

    this.basketSource.next({ ...basket });
    this.calculateTotal();

    this.setBasket(basket).subscribe({
      next: (response) => console.log('Basket updated', response),
      error: (error) => console.error('Error updating basket', error)
    });
  }

  private createBasket(): IBasket {
    const basket = new Basket();
    localStorage.setItem('basket_id', basket.id);
    return basket;
  }

  private addOrUpdateItem(items: IBasketItem[], itemToAdd: IBasketItem, quantity: number): IBasketItem[] {
    const item = items.find(x => x.id === itemToAdd.id);
    if (item) {
      item.quantity += quantity;
    } else {
      itemToAdd.quantity = quantity;
      items.push(itemToAdd);
    }
    return items;
  }

  private mapProductItemToBasketItem(product: IProduct, quantity: number): IBasketItem {
    return {
      id: product.id,
      name: product.name,
      price: product.newPrice,
      quantity: quantity,
      image: product.photos && product.photos.length > 0 ? product.photos[0].imageName : '',
      category: product.categoryName,
      description: product.description || ''
    };
  }

  incrementBasketItemQuantity(item: IBasketItem) {
    const basket = this.getCurrentBasketValue();
    if (!basket) return;

    const itemIndex = basket.items.findIndex(i => i.id === item.id);
    if (itemIndex !== -1) {
      basket.items[itemIndex].quantity++;

      this.basketSource.next({ ...basket });
      this.calculateTotal();

      this.setBasket(basket).subscribe();
    }
  }

  DecrementBasketItemQuantity(item: IBasketItem) {
    const basket = this.getCurrentBasketValue();
    if (!basket) return;

    const itemIndex = basket.items.findIndex(i => i.id === item.id);
    if (itemIndex !== -1) {
      if (basket.items[itemIndex].quantity > 1) {
        basket.items[itemIndex].quantity--;

        this.basketSource.next({ ...basket });
        this.calculateTotal();

        this.setBasket(basket).subscribe();
      } else {
        this.removeItemFormBasket(item);
      }
    }
  }

  removeItemFormBasket(item: IBasketItem) {
    const basket = this.getCurrentBasketValue();
    if (!basket) return;

    if (basket.items.some(i => i.id === item.id)) {
      basket.items = basket.items.filter(i => i.id !== item.id);
      if (basket.items.length > 0) {
        this.basketSource.next({ ...basket });
        this.calculateTotal();

        this.setBasket(basket).subscribe();
      } else {
        this.DeleteBasketItem(basket);
      }
    }
  }

  DeleteBasketItem(basket: IBasket) {
    return this.http.delete(this.baseUrl + 'Basket/' + basket.id).subscribe({
      next: () => {
        this.clearLocalBasket();
      },
      error: (err) => {
        console.error(err);
        this.clearLocalBasket();
      }
    });
  }

  clearLocalBasket() {
    this.basketSource.next(null);
    this.basketSourceTotal.next(null);
    localStorage.removeItem('basket_id');
  }
}
