import {Component, OnInit} from '@angular/core';
import {NavbarComponent} from "../navbar/navbar.component";
import {PdfViewerModule} from "ng2-pdf-viewer";
import {ActivatedRoute, Router} from "@angular/router";
import {SessionService} from "../services/session/session.service";
import {catchError, min, Subscription, throwError, TimeInterval} from "rxjs";
import {WebSocketService} from "../services/webSocket/web-socket.service";
import {Duration, parse} from "tinyduration";
import {clearInterval} from "stompjs";
import humanizeDuration from "humanize-duration";
import {HotToastService} from "@ngxpert/hot-toast";

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
   private connectionStatusSubscription !: Subscription;

  constructor(private toast : HotToastService ,private webSocket : WebSocketService,private route : ActivatedRoute , private router : Router , private sessionService : SessionService ) {}

  async ngOnInit(): Promise<void> {
    try {
      await this.webSocket.connect();
      window.onbeforeunload = (event) => {
        if (this.webSocket.isConnected()) {
          this.updateSession("offload");
          localStorage.removeItem("sessionData");
        }
        this.stopTimer();
        return null;
      };
      this.route.queryParams.subscribe(param => {
        this.sessionId = param["sessionId"];
        this.visitorId = param["visitorId"];
        this.fetchSession();
      });

      this.connectionStatusSubscription = this.webSocket.connectionStatus().subscribe((status)=>{
        if(!status){
          this.stopTimer();
          this.saveSessionDataToLocalStorage();
          this.webSocket.handleReconnect();
        }else if(status){
          const remainingSession = this.getSessionDataFromLocalStorage();
          console.log(remainingSession);
          if(remainingSession){
            this.toast.success("connection back",{position : 'top-right'});
            this.sessionId = remainingSession.sessionId;
            this.remainingTime = remainingSession.remainingTime;
            this.startTimer();
          }
        }
      });
    } catch (error) {
      console.error('WebSocket connection failed:', error);
    }
  }


  private saveSessionDataToLocalStorage() {
      const sessionData = {
        sessionId : this.sessionId,
        remainingTime : this.remainingTime
      }
      localStorage.setItem("sessionData",JSON.stringify(sessionData));
  }

  private getSessionDataFromLocalStorage() : {sessionId : string , remainingTime : number} | null {
      const sessionData = localStorage.getItem("sessionData");
      if(sessionData) return JSON.parse(sessionData);
      return null;
  }


  ngOnDestroy(): void {
    this.updateSession("destroy");
    this.stopTimer();
    this.webSocket.disconnect();
    if (this.connectionStatusSubscription) {
      this.connectionStatusSubscription.unsubscribe();
    }
    localStorage.removeItem("sessionData");
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
    if(this.webSocket.isConnected())
      this.currentPage++;
  }

  previousPage(){
    if(this.webSocket.isConnected())
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
      console.log(response.remainingTime);
      console.log(parse(response.remainingTime.toString()));
      this.remainingTime = this.convertToSeconds(parse(response.remainingTime.toString()));
      if(this.webSocket.isConnected()){
        this.startNewSession(response.id);
      }
      this.startTimer();
    })
  }

  private stopTimer(){
    clearTimeout(this.timerInterval);
  }

  private startTimer() {

    this.timerInterval = setInterval (() => {
      if(this.remainingTime <= 0){
        this.toast.warning("session is over",{position : 'top-right'});
        this.stopSession("fdfdfd45");
        this.webSocket.disconnect();
        this.connectionStatusSubscription.unsubscribe();
        this.remainingTime = 0;
        clearInterval(this.timerInterval);
        localStorage.removeItem("sessionData");
      }
      this.decreaseRemainingTime();
    } , 1000);
  }

  private decreaseRemainingTime() {
    const remainingSeconds = this.remainingTime;

    if (remainingSeconds > 0) {
      const newTotalSeconds = remainingSeconds - 1;
      this.remainingTime = newTotalSeconds;
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
    const hours = duration.hours ?? 0;
    const minutes = duration.minutes ?? 0;
    const seconds = duration.seconds ?? 0;
    return (hours * 3600) + (minutes * 60) + (seconds);
  }

  public convertToHumanReadableTime(){
    const hours = Math.floor(this.remainingTime / 3600);
    const minutes = Math.floor((this.remainingTime % 3600) / 60);
    const seconds = this.remainingTime % 60;
    return {hours , minutes , seconds};
  }

}
