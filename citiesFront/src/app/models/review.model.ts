import {book} from "./book.model";
import {Person} from "./person.model";

export interface Review {
  id : string;
  book : book;
  person : Person;
  review : string;
  stars : number;
  createdAt : string;
}
