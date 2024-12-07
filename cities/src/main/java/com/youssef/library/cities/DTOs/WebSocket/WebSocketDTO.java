package com.youssef.library.cities.DTOs.WebSocket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WebSocketDTO {
    private String sessionId;
    private String visitorId;
    private long remainingTime;
    private long currentPage;
}
