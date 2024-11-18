import {Component, OnInit} from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {PdfViewerModule} from "ng2-pdf-viewer";
import {ActivatedRoute, Router} from "@angular/router";
import {SessionService} from "../services/session/session.service";
import {catchError, Subscription, throwError, TimeInterval} from "rxjs";
import {WebSocketService} from "../services/webSocket/web-socket.service";
import {Duration, parse} from "tinyduration";
import {clearInterval} from "stompjs";
import humanizeDuration from "humanize-duration";

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
   remainingTime !: number;
   private timerInterval : any;

  constructor(private webSocket : WebSocketService,private route : ActivatedRoute , private router : Router , private sessionService : SessionService ) {}

  async ngOnInit(): Promise<void> {
    try {
      await this.webSocket.connect();
      window.onbeforeunload = (event) => {
        if (this.webSocket.isConnected()) {
          this.updateSession("offload");
        }
        return null;
      };
      this.route.queryParams.subscribe(param => {
        this.sessionId = param["sessionId"];
        this.visitorId = param["visitorId"];
        this.fetchSession();
      });
    } catch (error) {
      console.error('WebSocket connection failed:', error);
    }
  }



  ngOnDestroy(): void {
    this.updateSession("destroy");
    this.webSocket.disconnect();
  }



  public startNewSession(sessionId : string) {
    this.webSocket.startSession(sessionId);
  }

  public updateSession(sessionId : string){
    this.webSocket.updateSession(sessionId);
  }

  public stopSession(sessionId : string) {
    this.webSocket.stopSession(sessionId);
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
      this.remainingTime = this.convertToSeconds(parse(response.remainingTime.toString()));
      console.log(this.remainingTime);
      if(this.webSocket.isConnected()){
        this.startNewSession(response.id);
      }
      // this.startTimer();
    })
  }

  private startTimer() {
    if(this.timerInterval) {
      clearInterval(this.timerInterval);
    }

    this.timerInterval = setInterval (() => {
      if(this.remainingTime <= 0){
        alert("time is up lil bro");
        this.updateSession("update1234");
        this.webSocket.disconnect();
        clearInterval(this.timerInterval);
      }
      this.decreaseRemainingTime();
    } , 1000);
  }

  private decreaseRemainingTime() {
    const remainingSeconds = this.remainingTime;

    if (remainingSeconds > 0) {
      const newTotalSeconds = remainingSeconds - 1;
      this.remainingTime = newTotalSeconds;
      console.log(this.formatedRemainingTime());
    } else {
      // If total seconds is zero or less, set to zero
      this.remainingTime = 0;
    }
  }

  public formatedRemainingTime() {
    const timeConverted = humanizeDuration(this.remainingTime * 1000 , {
      language : 'en',
      units : ["h","m","s"],
      round : true
    });
    return timeConverted;
  }

  private convertToSeconds(duration: Duration) : number{
    return (<number>duration.hours * 3600) + (<number>duration.minutes * 60) + (<number>duration.minutes);
  }



}
