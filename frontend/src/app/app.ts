import { HttpClient } from '@angular/common/http';
import { Component, OnInit, signal } from '@angular/core';
import { IProduct } from './shared/Models/Product';
import { IPagnation } from './shared/Models/Pagnation';

@Component({
  selector: 'app-root',
  standalone: false,
  styleUrl: './app.scss',
  templateUrl: './app.html',
})
export class App implements OnInit {

  constructor() {}

  ngOnInit(): void {

  }
  protected readonly title = signal('Client');
}
