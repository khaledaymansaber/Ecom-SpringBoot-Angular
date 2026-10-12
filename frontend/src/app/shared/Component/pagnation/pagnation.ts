import { Component } from '@angular/core';
import {  Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-pagnation',
  standalone: false,
  styleUrl: './pagnation.scss',
  templateUrl: './pagnation.html',
})
export class Pagnation {
  @Input() pageSize: number = 0;
  @Input() TotalCount: number = 0;

  // Output عشان نبلغ الـ Parent بتغيير الصفحة
  @Output() pageChanged = new EventEmitter<number>();

  onPagerChange(event: any) {
    // ngx-bootstrap بيبعت الايفنت جواه رقم الصفحة في event.page
    this.pageChanged.emit(event.page);
  }
}
