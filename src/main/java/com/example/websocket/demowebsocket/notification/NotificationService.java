package com.example.websocket.demowebsocket.notification;

public interface NotificationService<T> {
    public void sendInfo(String from, String to, T payload);
}
