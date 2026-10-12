export interface IDeliveryMethod {
  id: number;
  name: string;
  price: number;
  description: string;
  deliveryTime: string;
}

export interface IShippingAddress {
  firstName: string;
  lastName: string;
  street: string;
  city: string;
  state: string;
  zipCode: string;
}

export interface IOrderItem {
  id: number;
  productItemId: number;
  productName: string;
  mainImage: string;
  price: number;
  quantity: number;
}

export interface IOrder {
  id: number;
  buyerEmail: string;
  orderDate: string;
  shippingAddress: IShippingAddress;
  deliveryMethod: IDeliveryMethod;
  shippingPrice: number;
  subTotal: number;
  total: number;
  status: string;
  orderItems: IOrderItem[];
}

export interface IOrderToCreate {
  basketId: string;
  deliveryMethodId: number;
  shippingAddress: IShippingAddress;
}
