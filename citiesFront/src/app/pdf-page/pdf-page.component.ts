import {Component, OnInit} from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {PdfViewerModule} from "ng2-pdf-viewer";
import {ActivatedRoute, Router} from "@angular/router";
import {SessionService} from "../services/session/session.service";
import {catchError, Subscription, throwError} from "rxjs";
import {WebSocketService} from "../services/webSocket/web-socket.service";
import {Duration} from "tinyduration";

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
export class PdfPageComponent implements OnInit{

  // pdfSource : string = "https://vadimdez.github.io/ng2-pdf-viewer/assets/pdf-test.pdf";
   sessionId !: string;
   pdfSource !: string;
   currentPage !: number;
   errorMessage !: string;
   visitorId !: string;
   private sessionSubscription !: Subscription;
  public sessions: any[] = [];


  constructor(private webSocket : WebSocketService,private route : ActivatedRoute , private router : Router , private sessionService : SessionService ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(param => {
      this.sessionId = param["sessionId"];
      this.visitorId = param["visitorId"];
      this.fetchSession();
    })
  }

  ngOnDestroy(): void {
    this.sessionSubscription.unsubscribe();
  }

  private handleSessionExpiry() {
    // Logic to handle session expiration
    alert('Your session has expired!');
    // Optionally remove expired sessions from this.sessions
  }

  public startNewSession(sessionId : string) {
    this.webSocket.startSession(sessionId);
  }


  nextPage(){
    this.currentPage++;
  }

  previousPage(){
    this.currentPage--;
  }


  private fetchSession() {
    this.sessionService.getSession(this.sessionId).pipe(
      catchError(err => {
        this.errorMessage = err.error.message;
        return throwError(()=>new Error(err.message));
      })
    ).subscribe(response => {
      this.pdfSource = response.book.pdfFile;
      this.currentPage = response.currentPage;
      this.startNewSession(response.id);
    })
  }
}
