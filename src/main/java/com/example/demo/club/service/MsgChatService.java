package com.example.demo.club.service;

import com.example.demo.club.dto.ChatRoomDTO;
import com.example.demo.club.dto.ChatRoomMap;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MsgChatService {
    public ChatRoomDTO createChatRoom(String roomName, int maxUserCnt) {
        // roomName 와 roomPwd 로 chatRoom 빌드 후 return
        ChatRoomDTO room = ChatRoomDTO.builder()
                .roomId(UUID.randomUUID().toString())
                .roomName(roomName)
                .userCount(0) // 채팅방 참여 인원수
                .maxUserCnt(maxUserCnt) // 최대 인원수 제한
                .build();

        room.setUserList(new ConcurrentHashMap<String,String>());

        // msg 타입이면 ChatType.MSG
        room.setChatType(ChatRoomDTO.ChatType.MSG);

        // map 에 채팅룸 아이디와 만들어진 채팅룸을 저장
        ChatRoomMap.getInstance().getChatRooms().put(room.getRoomId(), room);

        return room;
    }

    // 채팅방 유저 리스트에 유저 추가
    public String addUser(Map<String, ChatRoomDTO> chatRoomMap, String roomId, String userName){
        ChatRoomDTO room = chatRoomMap.get(roomId);
        String userUUID = UUID.randomUUID().toString();

        // 아이디 중복 확인 후 userList 에 추가
        //room.getUserList().put(userUUID, userName);

        // hashmap 에서 concurrentHashMap 으로 변경
        ConcurrentHashMap<String, String> userList = (ConcurrentHashMap<String, String>)room.getUserList();
        userList.put(userUUID, userName);


        return userUUID;
    }
}
