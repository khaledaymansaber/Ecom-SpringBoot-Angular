import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IPagnation } from '../shared/Models/Pagnation';
import { ICategory } from '../shared/Models/Category';
import { ProductParams } from '../shared/Models/ProductParams';
import { IProduct } from '../shared/Models/Product';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ShopService {
  baseURL = environment.baseURL;

  constructor(private http: HttpClient) { }

  getProduct(productParams: ProductParams) {
    let params = new HttpParams();
    if (productParams.selectedCategoryId && productParams.selectedCategoryId > 0) {
      params = params.append('categoryId', productParams.selectedCategoryId.toString());
    }
    if (productParams.SortSelected) {
      params = params.append('Sort', productParams.SortSelected);
    }
    if (productParams.Search) {
      params = params.append('search', productParams.Search);
    }
    params = params.append('pageNumber', productParams.pageNumber.toString());
    params = params.append('pageSize', productParams.pageSize.toString());

    return this.http.get<IPagnation>(this.baseURL + 'Products/get-all', { params });
  }

  getCategory() {
    return this.http.get<ICategory[]>(this.baseURL + 'Categories/get-all');
  }

  getProductDetails(id: number) {
    return this.http.get<IProduct>(this.baseURL + 'Products/get-by-id/' + id);
  }
}
