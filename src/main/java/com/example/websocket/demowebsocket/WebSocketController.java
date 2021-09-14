package com.example.websocket.demowebsocket;

import java.io.IOException;

import com.example.websocket.demowebsocket.notification.websocket.WebSocketServer;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/websocket")
public class WebSocketController {

    @PostMapping("/restful/push/{cid}/{message}")
    public ResponseEntity<?> pushToWebByRest(@PathVariable(value = "cid") String cid,@PathVariable(value = "message") String message) {
        TestDTO data = TestDTO.builder().data(message).from("server").to(cid).build();
        try {
            WebSocketServer.sendInfo(message, cid);
            
            WebSocketServer.sendInfo(data.toString(), cid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(data);
    }
}
