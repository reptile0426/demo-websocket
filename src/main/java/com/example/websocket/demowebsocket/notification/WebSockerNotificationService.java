package com.example.websocket.demowebsocket.notification;

import java.io.IOException;

import com.example.websocket.demowebsocket.notification.websocket.WebSocketServer;

import org.springframework.stereotype.Service;

@Service
public class WebSockerNotificationService implements NotificationService<String> {

    @Override
    public void sendInfo(String from, String to, String payload) {
        try {
            WebSocketServer.sendInfo(payload, to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
