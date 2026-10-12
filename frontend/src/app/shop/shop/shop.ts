import { Component, OnInit, signal } from '@angular/core'; // قمنا بإضافة signal هنا
import { ShopService } from '../shop';
import { IProduct } from '../../shared/Models/Product';
import { IPagnation } from '../../shared/Models/Pagnation';
import { ICategory } from '../../shared/Models/Category';
import { ProductParams } from '../../shared/Models/ProductParams';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-shop',
  standalone: false,
  styleUrl: './shop.scss',
  templateUrl: './shop.html',
})
export class Shop implements OnInit {

  products = signal<IProduct[]>([]);
  Categories = signal<ICategory[]>([]);
  ProductParams = new ProductParams();
  TotalCount!:number
  //selectedCategoryId: number = 0;
  //SortSelected: string = 'Name';
 // Search!:string


  constructor(private shopService: ShopService,private toast:ToastrService) {}

  ngOnInit(): void {

    this.getProducts();
    this.getCategory();

  }

  getProducts() {
    this.shopService.getProduct( this.ProductParams).subscribe({
      next: (value:IPagnation) => {

        this.products.set(value.data);
        this.TotalCount=value.totalCount
        this.ProductParams.pageNumber=value.pageNumber
        this.ProductParams.pageSize=value.pageSize
        this.toast.success( "Product Loaded Successfully","SUCCESS")
      },

  error: (err) => {
    console.error("Error loading products:", err);
  }
    });
  }

  getCategory() {
    this.shopService.getCategory().subscribe({
      next: (value:ICategory[]) => {
        this.Categories.set(value);
      }
    });
  }
  selectedById(categoryId:number){
  this.ProductParams.selectedCategoryId=categoryId;
  this.getProducts();
  }
SortingOption = [
    { name: "Name", value: 'Name' },
    { name: "Price: min-max", value: 'PriceAsn' },
    { name: "Price: max-min", value: 'PriceDes' }
  ]

SortingByPrice(sort: Event) {
    this.ProductParams.SortSelected = (sort.target as HTMLInputElement).value;
    this.getProducts();
  }
  OnSearch(Search:string){
    this.ProductParams.Search=Search
    this.getProducts()
  }
  ResetValue(searchInput: HTMLInputElement) {
    searchInput.value = '';
    this.ProductParams.Search = '';
    this.ProductParams.SortSelected = 'Name';
    this.ProductParams.selectedCategoryId = 0;
    this.getProducts();
  }
  OnChangePage(pageNumber: number) {

    if (this.ProductParams.pageNumber !== pageNumber) {
      this.ProductParams.pageNumber = pageNumber;

      this.getProducts();
    }
  }
}
