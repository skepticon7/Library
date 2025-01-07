import {DatePipe} from "@angular/common";

export interface Person {
  id : string;
  name : string;
  surname : string;
  email : string;
  gender : string;
  birthDate : DatePipe;
  type : string;
  libraryId : string;
}
