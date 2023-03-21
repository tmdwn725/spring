package com.example.demo.club.service;

import com.example.demo.club.common.ModelMapperUtil;
import com.example.demo.club.domain.Chat;
import com.example.demo.club.domain.ChatRoom;
import com.example.demo.club.domain.ClubInfo;
import com.example.demo.club.domain.Member;
import com.example.demo.club.dto.*;
import com.example.demo.club.repository.ChatRepository;
import com.example.demo.club.repository.ChatRoomRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    private final ModelMapper modelMapper;

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
        /*log.info("cnt {}",ChatRoomMap.getInstance().getChatRooms().get(roomId).getUserCount());

        ChatRoomDTO room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
        room.setChatRoomSeq(chatRoomSeq);
        room.setRoomName(chatRoom.getClub().getClubNm());
        room.setUserCount(room.getUserCount()+1);
        room.getUserList().put(senderId, sender);

         */
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

    public ChatRoomDTO findChatRoom(Long chatRoomSeq) {
        ChatRoom room = chatRoomRepository.selectChatRoomByClubSeq(chatRoomSeq);
        ChatRoomDTO chat =  new ChatRoomDTO();
        chat.setChatRoomSeq(room.getChatRoomSeq());
        //room.setRoomName(chatRoom.getClub().getClubNm());
        //ChatRoomDTO chat = modelMapper.map(room,ChatRoomDTO.class);
        return  chat;
    }

    public List<ChatDTO> findChatList(Long clubSeq) {
        List<Chat> list = chatRepository.selectChatListByClubSeq(clubSeq);
        List<ChatDTO> selectChatList = new ArrayList<ChatDTO>();
        /*if (list != null){
            selectChatList= ModelMapperUtil.mapAll(list, ChatDTO.class);
        }*/

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

}
