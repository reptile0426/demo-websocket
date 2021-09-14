package com.example.websocket.demowebsocket.notification.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.example.websocket.demowebsocket.ReceivedDataProcess;
import com.example.websocket.demowebsocket.TestDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ServerEndpoint(value = "/notification/websocket/{sid}")
public class WebSocketServer {

    private static  CopyOnWriteArraySet<WebSocketServer> webSocketMap = new CopyOnWriteArraySet<WebSocketServer>();
    private Session session;
    private String sid;

    ///import! why do this : https://blog.csdn.net/j1231230/article/details/114641956
    private static ReceivedDataProcess receivedDataProcess;

    @Autowired
    public void setReceivedDataProcess(ReceivedDataProcess receivedDataProcess) {
        WebSocketServer.receivedDataProcess = receivedDataProcess;
    }

    @OnOpen
    public void OnOpen(Session session, @PathParam("sid") String sid) throws IOException {
        this.session = session;
        this.sid = sid;
        webSocketMap.add(this);
        try {
            //send connect message
            sendMessage("connect complete");
            log.info("onOpen, sid = " + sid );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose() {
        
        webSocketMap.remove(this);
        log.info("onClose , sid = " + sid);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("receive message = " + message + " from : " + sid);

        receivedDataProcess.sendBack(TestDTO.builder().data(message).from(sid).to("server").build());

    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("error happened!!");
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    private void sendMessage(Object obj) throws IOException, EncodeException {
        session.getBasicRemote().sendObject(obj);
    }

    public static void sendInfo(String message, String sid) throws IOException  {
        log.info("sendInfo" + sid + ", message : " + message + " link count = " + webSocketMap.size());

        for (WebSocketServer item : webSocketMap) {
            try {
                if (sid == null) {
//                    item.sendMessage(message);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                    
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static void sendInfoObject(Object obj, String sid) throws EncodeException {
        log.info("sendInfo" + sid + ", message :" + obj.toString() + " link count = " + webSocketMap.size());

        for (WebSocketServer item : webSocketMap) {
            try {
                if (sid == null) {
//                    item.sendMessage(message);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(obj);
                    
                }
            } catch (IOException e) {
                continue;
            }
        }
    }


}
