import { IShippingAddress } from './Order';

export interface IUserProfile {
  id: number;
  displayName: string;
  userName: string;
  email: string;
  address?: IShippingAddress;
}

export interface IUpdateProfile {
  displayName: string;
}
