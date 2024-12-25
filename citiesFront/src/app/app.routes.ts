import { Routes } from '@angular/router';
import {CitiesComponent} from "./cities/cities.component";
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
import {LoginComponent} from "./login/login.component";
import {SignupComponent} from "./signup/signup.component";
import {authGuard} from "./utils/auth.guard";

export const routes: Routes = [
  { path: "", component: HomeComponent },
  { path: "cities", component: CitiesComponent },
  { path: "libraries", component: LibrariesComponent },
  { path: "shelves", component: ShelvesComponent },
  { path: "books", component: BooksComponent },
  { path: "book", component: BookComponent },
  { path: "success", component: SuccessComponent },
  { path: "cancel", component: CancelComponent },
  { path: "sessions", canActivate: [authGuard], component: SessionsComponent },
  { path: "pdfBook", canActivate: [authGuard], component: PdfPageComponent },
  { path: "login", component: LoginComponent },
  { path: "signup", component: SignupComponent },

  {
    path: "librarian",
    canActivate: [authGuard],
    children: [
      { path: "newCity", component: NewCityComponent },
      { path: "newLibrary", component: NewLibraryComponent },
      { path: "newShelf", component: NewShelfComponent },
      { path: "newBook", component: NewBookComponent },
    ],
  },
];

