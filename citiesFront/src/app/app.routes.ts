import { Routes } from '@angular/router';
import {CitiesComponent} from "./cities/cities.component";
import {AppComponent} from "./app.component";
import {HomeComponent} from "./home/home.component";
import {NewCityComponent} from "./new-city/new-city.component";
import {LibrariesComponent} from "./libraries/libraries.component";
import {NewLibraryComponent} from "./new-library/new-library.component";
import {ShelvesComponent} from "./shelves/shelves.component";
import {NewShelfComponent} from "./new-shelf/new-shelf.component";
import {BooksComponent} from "./books/books.component";
import {NewBookComponent} from "./new-book/new-book.component";
import {BookComponent} from "./book/book.component";
import {SuccessComponent} from "./success/success.component";
import {CancelComponent} from "./cancel/cancel.component";
import {SessionsComponent} from "./sessions/sessions.component";
import {PdfPageComponent} from "./pdf-page/pdf-page.component";

export const routes: Routes = [
  {path : "cities" , component : CitiesComponent},
  {path:"" , component : HomeComponent},
  {path:"newCity",component : NewCityComponent},
  {path : "libraries",component : LibrariesComponent},
  {path : "newLibrary",component : NewLibraryComponent},
  {path : "shelves",component : ShelvesComponent},
  {path : "newShelf",component : NewShelfComponent},
  {path : "books",component : BooksComponent},
  {path : "newBook",component : NewBookComponent},
  {path : "book",component:BookComponent},
  {path : "success",component : SuccessComponent},
  {path : "cancel" , component : CancelComponent},
  {path : "sessions",component : SessionsComponent},
  {path : "pdfBook" , component : PdfPageComponent}
];
