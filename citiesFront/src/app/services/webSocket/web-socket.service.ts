import {Injectable, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment.development";
import SockJS from "sockjs-client";
import {Client} from "@stomp/stompjs";
import {Duration} from "tinyduration";
import {Subject} from "rxjs";
import {HotToastService} from "@ngxpert/hot-toast";



@Injectable({
  providedIn: 'root'
})
export class WebSocketService{
  private stompClient : any;
  private reconnectAttempts: number = 0;
  private maxReconnectAttempts: number = 5;
  private reconnectDelay: number = 5000;

  private connectionStatus$ = new Subject<boolean>();

  constructor(private toast : HotToastService) {
  }

  public connect(): Promise<void> {
    return new Promise((resolve, reject) => {
      const socket = new SockJS('http://localhost:8080/session'); // Your WebSocket endpoint
      this.stompClient = new Client({
        webSocketFactory: () => socket,
        debug: (str) => console.log(str),
        onConnect: (frame) => {
          console.log('Connected to WebSocket ' + frame);
          this.connectionStatus$.next(true);
          resolve();
        },
        onDisconnect : () => {
          this.connectionStatus$.next(false);
        },
        onStompError: (frame) => {
          console.error('Broker error: ' + frame.headers['message']);
          this.connectionStatus$.next(false);
          reject(frame.headers['message']);
        },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
      });


      this.stompClient.onWebSocketClose = ()=>{
        console.error('WebSocket connection closed subscription.');
        this.connectionStatus$.next(false);
      }

      this.stompClient.activate();

    });
  }



  public isConnected() : boolean {
    return this.stompClient && this.stompClient.connected;
  }

  public connectionStatus() {
    return this.connectionStatus$;
  }


  public handleReconnect() {
    if(this.reconnectAttempts < 1)
      this.toast.warning("connection lost", {position : 'top-right'});
    if (this.reconnectAttempts < this.maxReconnectAttempts) {
      console.log(`Attempting to reconnect... (${this.reconnectAttempts + 1}/${this.maxReconnectAttempts})`);

      setTimeout(() => {
        this.reconnectAttempts++;
        this.connect(); // Attempt to reconnect
      }, this.reconnectDelay);
    } else {
      console.error('Max reconnect attempts reached. Please check your connection.');
    }
  }

  public disconnect(){
    if(this.isConnected()){
      this.stompClient.deactivate();
    }else{
      console.warn('WebSocket client is not active. No need to disconnect.');
    }
  }

  public startSession(sessionId : string , remainingTime : number , visitorId : string , currentPage : number){
    this.stompClient.publish({ destination: '/app/start', body: JSON.stringify({ sessionId , remainingTime , visitorId , currentPage})});
  }

  public stopSession(sessionId: string , visitorId : string , currentPage : number) {
    this.stompClient.publish({destination : '/app/stop' , body : JSON.stringify({sessionId , visitorId , currentPage})});
  }

  public updateSession(sessionId: string , remainingTime : number , visitorId : string , currentPage : number) {
    this.stompClient.publish({destination : '/app/update' , body : JSON.stringify({sessionId , remainingTime , visitorId , currentPage})});
  }

}
