package com.ssafy.d204.chat.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;


/*
        NORMAL_CLOSURE(1000),
        GOING_AWAY(1001),
        PROTOCOL_ERROR(1002),
        CANNOT_ACCEPT(1003),
        RESERVED(1004),
        NO_STATUS_CODE(1005),
        CLOSED_ABNORMALLY(1006),
        NOT_CONSISTENT(1007),
        VIOLATED_POLICY(1008),
        TOO_BIG(1009),
        NO_EXTENSION(1010),
        UNEXPECTED_CONDITION(1011),
        SERVICE_RESTART(1012),
        TRY_AGAIN_LATER(1013),
        TLS_HANDSHAKE_FAILURE(1015);
 */

@ServerEndpoint("/msgServer/{userId}")
@CrossOrigin("*")
@Component
//@Scope("prototype")
public class WebRtcSocketController {

    private volatile static int onlineCount = 0;
    private static final ConcurrentHashMap<String, List<Session>> webSocketMap = new ConcurrentHashMap<>();
    // above 2 fields need to be thread safe.
    private Session session;
//    private String userId = "";

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
//        this.userId = userId;
        if (!webSocketMap.containsKey(userId)) {
            webSocketMap.put(userId, new ArrayList<Session>());
        }
        webSocketMap.get(userId).add(session);
//        webSocketMap.put(userId, session);
//        System.out.println(userId + "-Connection established successfully...");
    }

    @OnMessage
    public void onMessage(String message, @PathParam("userId") String userId,
        Session requestSession) {
        // retry 5 times.
        int retry = 5;
        boolean isSuccess = false;
        while (retry-- > 0) {
            try {
                this.sendMessage(message, userId, requestSession);
                isSuccess = true;
//                break;
            } catch (IOException e) {
                // message 전송 실패시 어떤 행위를 해야할 것인가
//                System.err
//                    .println("Retrying " + (5 - retry) + " times establish connection. " + userId);
            }
            if (isSuccess) {
                break;
            }
        }
        if (!isSuccess) {
            if (webSocketMap.containsKey(userId)) {
                List<Session> sessions = webSocketMap.get(userId);
                for (Session session : sessions) {
                    if (session != null) {
                        closeSession(session, new CloseReason(
                            CloseReason.CloseCodes.TRY_AGAIN_LATER,
                            "SERVER IS BUSY. PLEASE TRY LATER"
                        ));
                    }
                }
                webSocketMap.remove(userId);
            }
            // let the clients know what happened.
        }
    }

    @OnError
    public void onError(Throwable error, @PathParam("userId") String userId) {
//        System.out.println("Connection has a problem. id : " + userId);
        if (webSocketMap.containsKey(userId)) {
            for (Session session : webSocketMap.get(userId)) {
                closeSession(session, new CloseReason(
                        CloseReason.CloseCodes.CLOSED_ABNORMALLY,
                        "SOMETHING GOES WRONG. PLEASE TRY AGAIN"
                    )
                );
            }
            webSocketMap.remove(userId);
        }
        error.printStackTrace();
    }

    @OnClose
    public void onClose(@PathParam("userId") String userId) {
//        System.out.println("Connection closed. id : " + userId);
        if (webSocketMap.containsKey(userId)) {
            for (Session session : webSocketMap.get(userId)) {
                closeSession(session, new CloseReason(
                        CloseReason.CloseCodes.NORMAL_CLOSURE,
                        "CONNECTION CLOSED NORMALLY"
                    )
                );
            }
            webSocketMap.remove(userId);
        }
    }

    private void closeSession(Session session, CloseReason reason) {
        try {
            session.close(reason);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearDeadSession() {
        Enumeration<String> keys = webSocketMap.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (webSocketMap.get(key) == null) {
                webSocketMap.remove(key);
                System.err.println(key + " : null");
            } else {
                boolean isDead = false;
                for (Session session : webSocketMap.get(key)) {
                    if (session == null) {
                        isDead = true;
                        break;
                    }
                }
                if (isDead) {
                    webSocketMap.remove(key);
                }
            }
        }
    }

    /**
     * Realize server active push
     */
    public void sendMessage(String message, @PathParam("userId") String userId,
        Session requestSession) throws IOException {
        if (message.equals("Heartbeat")) {
            this.session.getBasicRemote().sendText(message);
        }
        List<Session> sessions = webSocketMap.get(userId);
        for (Session session : sessions) {
            if (requestSession.getId().equals(session.getId())) {

//                System.out.println("MY" + requestSession.getId());
                continue;
            }
            if (session.isOpen()) {
//                System.out.println("Send a message to: " + userId + " ,message: " + message);
                session.getBasicRemote().sendText(message);
            } else {
//                System.err.println(userId + ": not open");
                closeSession(session, new CloseReason(
                        CloseReason.CloseCodes.NORMAL_CLOSURE,
                        "CONNECTION ESTABLISHED"
                    )
                );
                webSocketMap.remove(userId);
            }
        }
        clearDeadSession();
    }

    /**
     * Send custom message. Please refer this when chat is implemented on the WebRTC.
     */
    public static void sendInfo(String message, @PathParam("userId") String userId)
        throws IOException {
        System.out
            .println(
                "Send a message to:" + userId + ",content:" + message + " count:" + onlineCount);
        if (!userId.isEmpty() && webSocketMap.containsKey(userId)) {
//            webSocketMap.get(userId).getBasicRemote().sendText(message);
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