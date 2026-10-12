import { IProduct } from "./Product"

export interface IPagnation {
  pageNumber: number
  totalCount: number
  pageSize: number
  data: IProduct[]
}

