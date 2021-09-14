package com.example.websocket.demowebsocket;

import com.example.websocket.demowebsocket.notification.WebSockerNotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReceivedDataProcess {
    
    @Autowired
    WebSockerNotificationService WebSockerNotificationService;

    public void senddata() {
        log.info("1231212");
    }

    @Async("reSendTaskExecutor")
    public void sendBack(TestDTO data) {
        try {
            Thread.sleep(1000);
            WebSockerNotificationService.sendInfo(data.getTo(), data.getFrom(), data.getData());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}
