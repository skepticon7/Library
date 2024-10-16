import { Component } from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {PdfViewerModule} from "ng2-pdf-viewer";

@Component({
  selector: 'app-pdf-page',
  standalone: true,
  imports: [
    NavbarComponent,
    PdfViewerModule
  ],
  templateUrl: './pdf-page.component.html',
  styleUrl: './pdf-page.component.css'
})
export class PdfPageComponent {
  pdfSource : string = "https://vadimdez.github.io/ng2-pdf-viewer/assets/pdf-test.pdf";
  currentPage : number = 1;


  nextPage(){
    this.currentPage++;
  }

  previousPage(){
    this.currentPage--;
  }


}
