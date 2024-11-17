import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment.development";
import SockJS from "sockjs-client";
import {Client} from "@stomp/stompjs";
import {Duration} from "tinyduration";



@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient : any;
  private sessionId !: string;
  constructor() {
    this.connect();
  }

  private connect() {
    const socket = new SockJS('http://localhost:8080/session'); // Your WebSocket endpoint
    this.stompClient = new Client({
      webSocketFactory: () => socket,
      debug: (str) => { console.log(str); },
      onConnect: (frame) => {
        console.log('Connected: ' + frame);
        this.sessionId = frame.headers['session-id'];
        },
      onStompError: (frame) => { console.error('Broker error: ' + frame.headers['message']); },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      connectHeaders: {},
      onWebSocketClose: () => { console.log('WebSocket closed'); }
    });

    this.stompClient.activate();
  }

  public startSession(sessionId : string){
    this.stompClient.publish({ destination: '/app/start', body: JSON.stringify({ sessionId })});
  }

}
