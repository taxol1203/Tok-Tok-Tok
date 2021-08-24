package com.ssafy.d204.chat.dao;
// import 생략....

import org.springframework.stereotype.Repository;

@Repository
public class ChatDao2 {
//
//    private Map<String, ChatSession> chatRoomMap;
//
//    @PostConstruct
//    private void init() {
//        chatRoomMap = new LinkedHashMap<>();
//    }
//
//    public List<ChatSession> findAllRoom() {
//        // 채팅방 생성순서 최근 순으로 반환
//        List chatRooms = new ArrayList<>(chatRoomMap.values());
//        Collections.reverse(chatRooms);
//        return chatRooms;
//    }
//
//    public ChatSession findRoomById(String id) {
//        return chatRoomMap.get(id);
//    }
//
//    public ChatSession createChatRoom() {
//        ChatSession chatSession = ChatSession.create();
////        System.out.println("asdf");
//        chatRoomMap.put(chatSession.getId(), chatSession);
//        return chatSession;
//    }
}