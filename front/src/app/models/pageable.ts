import {Sort} from "./sort";

export class Pageable {
  sort: Sort;
  offset: number;
  pageSize: number;
  pageNumber: number;
  paged: boolean;
  unpaged: boolean;

  constructor(sort,offset,pageSize,pageNumber, paged, unpaged) {
    this.sort = sort;
    this.offset = offset;
    this.pageSize = pageSize;
    this.pageNumber = pageNumber;
    this.paged = paged;
    this.unpaged = unpaged;
  }
}
