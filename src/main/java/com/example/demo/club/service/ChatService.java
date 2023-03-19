package com.example.demo.club.service;

import com.example.demo.club.domain.Chat;
import com.example.demo.club.domain.ChatRoom;
import com.example.demo.club.dto.ChatDTO;
import com.example.demo.club.dto.ChatRoomDTO;
import com.example.demo.club.dto.ChatRoomMap;
import com.example.demo.club.repository.ChatRepository;
import com.example.demo.club.repository.ChatRoomRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class ChatService {

    private final MsgChatService msgChatService;

    private final ChatRoomRepository chatRoomRepository;

    private final ChatRepository chatRepository;

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
    public void plusUserCnt(String roomId,String senderId,String sender){
        //log.info("cnt {}",ChatRoomMap.getInstance().getChatRooms().get(roomId).getUserCount());

        ChatRoom chatRoom = chatRoomRepository.selectChatRoomByClubSeq(Long.parseLong(roomId));
        ChatRoomDTO room = new ChatRoomDTO();
        //ChatRoomDTO room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
        room.setRoomId(roomId);
        room.setRoomName(chatRoom.getClub().getClubNm());
        room.setUserCount(room.getUserCount()+1);
        room.getUserList().put(senderId, sender);
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

    public void insertChat(ChatDTO chatDTO){
        Chat chat = new Chat();
        chat.getMember().setMemberSeq(chatDTO.getMemberSeq());
        chat.getChatRoom().setChatRoomSeq(chatDTO.getChatRoomSeq());
        chat.setMessage(chatDTO.getMessage());
        chatRepository.insertChat(chat);
    }

}
