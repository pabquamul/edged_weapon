import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

import {CartItem} from "../models/cart-item";
import {cartUrl} from "../config/api";
import {Product} from "../models/product";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private http: HttpClient) { }

  getCartItems(): Observable<CartItem[]>{
    //TODO mapping the obtained result to our CartItem props. (pipe() and map())
    return this.http.get<CartItem[]>(cartUrl);
  }

  addProductToCart(product: Product): Observable<any>{
    return this.http.post(cartUrl,{product});
}

}
