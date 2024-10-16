import {Shelf} from "./shelf.model";

export interface book{
  id : string;
  title : string;
  publisher : string;
  author : string;
  copies : number;
  description : string;
  pages : number;
  year : number;
  era : number;
  cover : string;
  halfPrice : number;
  onePrice : number;
  oneHalfPrice : number;
  shelf : Shelf;
  pdfFile : string;
}

export interface bookData {
  book : book,
  sessionCheck : boolean
}
