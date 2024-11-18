import {Injectable, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment.development";
import SockJS from "sockjs-client";
import {Client} from "@stomp/stompjs";
import {Duration} from "tinyduration";



@Injectable({
  providedIn: 'root'
})
export class WebSocketService{
  private stompClient : any;
  private sessionId !: string;
  private reconnectAttempts: number = 0;
  private maxReconnectAttempts: number = 5;
  private reconnectDelay: number = 5000;

  public connect(): Promise<void> {
    return new Promise((resolve, reject) => {
      const socket = new SockJS('http://localhost:8080/session'); // Your WebSocket endpoint
      this.stompClient = new Client({
        webSocketFactory: () => socket,
        debug: (str) => console.log(str),
        onConnect: (frame) => {
          console.log('Connected to WebSocket' + frame);
          resolve();
        },
        onDisconnect : () => {
          this.stopSession("fdfdfdfd5");
        },
        onStompError: (frame) => {
          console.error('Broker error: ' + frame.headers['message']);
          reject(frame.headers['message']);
        },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
      });

      this.stompClient.activate();
    });
  }

  public isConnected() : boolean {
    return this.stompClient && this.stompClient.connected;
  }

  private handleReconnect() {
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

  public startSession(sessionId : string){
    this.stompClient.publish({ destination: '/app/start', body: JSON.stringify({ sessionId })});
  }

  public stopSession(sessionId: string) {
    this.stompClient.publish({destination : '/app/stop' , body : JSON.stringify({sessionId})});
  }

  public updateSession(sessionId: string) {
    this.stompClient.publish({destination : '/app/update' , body : JSON.stringify({sessionId})});
  }
}
