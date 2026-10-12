export interface IProduct {
  id: number
  name: string
  description: string
  oldPrice: number
  newPrice: number
  categoryName: string
  photos: IPhoto[]
}

export interface IPhoto {
  id: number
  imageName: string
}
