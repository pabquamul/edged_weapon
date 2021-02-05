import {Product} from "./product";
import {Pageable} from "./pageable";
import {Sort} from "./sort";

export class Pagination {
  content: Product[];
  pageable: Pageable;
  hasNext: boolean;
  totalPages: number;
  totalElements: number;
  hasContent: boolean;
  last:boolean;
  size:number;
  sort: Sort;
  number: number;
  numberOfElements: number;
  first: boolean;
  empty: boolean;

  constructor(content, pageable, hasNext, totalPages, totalElements, hasContent, last, size, sort, number, numberOfElements,first, empty) {
    this.content = content;
    this.pageable = pageable;
    this.hasNext = hasNext;
    this.totalPages = totalPages;
    this.totalElements = totalElements;
    this.hasContent = hasContent;
    this.last = last;
    this.size = size;
    this.sort = sort;
    this.number = number;
    this.numberOfElements = numberOfElements;
    this.first = first;
    this.empty = empty;

  }

}
