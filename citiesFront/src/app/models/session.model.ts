import {book} from "./book.model";
import {Duration} from "tinyduration";



export interface Session {
  id : string;
  originalTime : Duration;
  remainingTime : Duration;
  book : book;
  sessionType : string;
  sessionStatus : string;
  price : number;
  currentPage : number;
}
