package com.example.demo.club.service;

import com.example.demo.club.domain.Chat;
import com.example.demo.club.domain.ClubInfo;
import com.example.demo.club.dto.*;
import com.example.demo.club.repository.ChatRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatRepository chatRepository;

    //private final ModelMapper modelMapper;

    // roomID 기준으로 채팅방 찾기
     /*public ChatRoomDTO findRoomById(String roomId){
        return ChatRoomMap.getInstance().getChatRooms().get(roomId);
    }

    // 채팅방 인원+1
    public void plusUserCnt(String roomId,String senderId,String sender){
       log.info("cnt {}",ChatRoomMap.getInstance().getChatRooms().get(roomId).getUserCount());

        ChatRoomDTO room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
        room.setChatRoomSeq(chatRoomSeq);
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
    }*/

    public List<ChatDTO> findChatList(Long clubSeq) {
        List<ChatDTO> selectChatList = chatRepository.selectChatListByClubSeq(clubSeq);
        return  selectChatList;
    }

    public void saveChat(ChatDTO chatDTO){
        LocalDateTime currentDate = LocalDateTime.now();
        Chat chat = new Chat();
        ClubInfo clubInfo = new ClubInfo();
        clubInfo.setClubInfoSeq(chatDTO.getClubInfoSeq());
        chat.setMessage(chatDTO.getMessage());
        chat.setSendDt(currentDate);
        chat.setClubInfo(clubInfo);
        chatRepository.save(chat);
    }
/*
    public ArrayList<String> getUserList(Map<String, ChatRoomDTO> chatRoomMap, String roomId){
        ArrayList<String> list = new ArrayList<>();

        ChatRoomDTO room = chatRoomMap.get(roomId);

        // hashmap 을 for 문을 돌린 후
        // value 값만 뽑아내서 list 에 저장 후 reutrn
        room.getUserList().forEach((key, value) -> list.add((String) value));
        return list;
    }*/

}
