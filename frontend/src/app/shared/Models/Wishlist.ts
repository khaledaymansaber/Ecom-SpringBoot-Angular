import { IProduct } from './Product';

export interface IWishlistItem {
  id: number;
  product: IProduct;
  addedAt: string;
}
