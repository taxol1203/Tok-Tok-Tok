package com.ssafy.d204.chat.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint("/msgServer/{userId}")
@CrossOrigin("*")
@Component
@Scope("prototype")
public class WebRtcSocketController {

    /**
     * Static variables, used to record the current number of online connections. It should be designed to be thread-safe.
     */
    private volatile static int onlineCount = 0;
    /**
     * The thread-safe Set of the concurrent package is used to store the MyWebSocket object corresponding to each client.
     */
    private static ConcurrentHashMap<String, Session> webSocketMap = new ConcurrentHashMap<>();
    /**
     * A connection session with a client needs to be used to send data to the client
     */
    private Session session;
    /**
     * Receive userId
     */
    private String userId = "";

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        /**
         * Connection is opened: add session to socket-map
         */
        webSocketMap.put(userId, session);
        System.out.println(userId + "-Connection established successfully...");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            this.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("Connection abnormal...");
        error.printStackTrace();
    }

    @OnClose
    public void onClose() {
        System.out.println("Connection closed");
    }

    /**
     * Realize server active push
     */
    public void sendMessage(String message) throws IOException {
        if (message.equals("Heartbeat")){
            this.session.getBasicRemote().sendText(message);
        }
        Enumeration<String> keys = webSocketMap.keys();
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            if (key.equals(this.userId)){
                System.err.println("my id " + key);
                continue;
            }
            if (webSocketMap.get(key) == null){
                webSocketMap.remove(key);
                System.err.println(key + " : null");
                continue;
            }
            Session sessionValue = webSocketMap.get(key);
            if (sessionValue.isOpen()){
                System.out.println("Send a message to: " + key + " ,message: " + message);
                sessionValue.getBasicRemote().sendText(message);
            }else {
                System.err.println(key + ": not open");
                sessionValue.close();
                webSocketMap.remove(key);
            }
        }
    }

    /**
     * Send custom message
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) throws IOException {
        System.out.println("Send a message to:" + userId + ",content:" + message + " count:"+ onlineCount);
        if (!userId.isEmpty() && webSocketMap.containsKey(userId)) {
            webSocketMap.get(userId).getBasicRemote().sendText(message);
//            webSocketServer.sendMessage(message);
        } else {
            System.out.println("user" + userId + ",not online!");
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebRtcSocketController.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebRtcSocketController.onlineCount--;
    }
}