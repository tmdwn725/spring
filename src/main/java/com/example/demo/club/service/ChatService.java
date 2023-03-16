package com.example.demo.club.service;

import com.example.demo.club.dto.ChatRoomDTO;
import com.example.demo.club.dto.ChatRoomMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class ChatService {

    private final MsgChatService msgChatService;

    // roomID 기준으로 채팅방 찾기
    public ChatRoomDTO findRoomById(String roomId){
        return ChatRoomMap.getInstance().getChatRooms().get(roomId);
    }

    // roomName 로 채팅방 만들기
    public ChatRoomDTO createChatRoom(String roomName, String roomPwd, boolean secretChk, int maxUserCnt, String chatType){

        ChatRoomDTO room;
        room = msgChatService.createChatRoom(roomName, maxUserCnt);

        return room;
    }

    // 채팅방 인원+1
    public void plusUserCnt(String roomId){
        log.info("cnt {}",ChatRoomMap.getInstance().getChatRooms().get(roomId).getUserCount());
        ChatRoomDTO room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
        room.setUserCount(room.getUserCount()+1);
    }

    // 채팅방 인원-1
    public void minusUserCnt(String roomId){
        ChatRoomDTO room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
        room.setUserCount(room.getUserCount()-1);
    }

    // maxUserCnt 에 따른 채팅방 입장 여부
    public boolean chkRoomUserCnt(String roomId){
        ChatRoomDTO room = ChatRoomMap.getInstance().getChatRooms().get(roomId);

        if (room.getUserCount() + 1 > room.getMaxUserCnt()) {
            return false;
        }

        return true;
    }

}
