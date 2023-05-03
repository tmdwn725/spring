package com.example.demo.club.controller;

import com.example.demo.club.dto.ChatDTO;
import com.example.demo.club.dto.ChatRoomDTO;
import com.example.demo.club.dto.ChatRoomMap;
import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.service.ChatService;
import com.example.demo.club.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    // 아래에서 사용되는 convertAndSend 를 사용하기 위해서 서언
    // convertAndSend 는 객체를 인자로 넘겨주면 자동으로 Message 객체로 변환 후 도착지로 전송한다.
    private final SimpMessageSendingOperations template;
    private final MemberService memberService;
    private  final ChatService chatService;

    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload ChatDTO chat, SimpMessageHeaderAccessor headerAccessor) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        MemberDTO member = memberService.selectMemberById(userId);
        // 채팅방 유저+1
        //chatService.plusUserCnt(chat.getChatRoomSeq(), chat.getSenderId(), chat.getSender());

        List<ChatDTO> chatList = chatService.findChatList(chat.getClubSeq());
        //room.setChatList(chatList);

        // 채팅방에 유저 추가 및 UserUUID 반환
        //String userUUID = msgChatService.addUser(ChatRoomMap.getInstance().getChatRooms(), chat.getRoomId(), chat.getSender());

        // 반환 결과를 socket session 에 userUUID 로 저장
        //headerAccessor.getSessionAttributes().put("userUUID", chat.getClubInfoSeq());
        headerAccessor.getSessionAttributes().put("clubSeq", chat.getClubSeq());

        chat.setMessage(member.getMemberNm() + " 님 입장!!");
        // 메시지 중복되는 현상 제거를 위한 코드 추가
        chat.setMessageId(UUID.randomUUID().toString());
        chatList.add(chat);
        template.convertAndSend("/sub/chat/room/" + Long.toString(chat.getClubSeq()), chat);
    }

    // 해당 유저
    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDTO chat) {
        log.info("CHAT {}", chat);
        chat.setMessage(chat.getMessage());

        chatService.saveChat(chat);
        // 메시지 중복되는 현상 제거를 위한 코드 추가
        chat.setMessageId(UUID.randomUUID().toString());
        chat.setChatSeq(Long.valueOf(0));
        template.convertAndSend("/sub/chat/room/" + Long.toString(chat.getClubSeq()), chat);
    }

    // 채팅방 입장 화면
    // 파라미터로 넘어오는 clubInfoSeq 를 확인후 해당 clubInfoSeq 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.
    @GetMapping("/chat/room")
    public String roomDetail(Model model, String clubInfoSeq){

        log.info("chatRoomSeq {}", clubInfoSeq);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        // principalDetails 가 null 이 아니라면 로그인 된 상태!!
        if (userName != null) {
            // 세션에서 로그인 유저 정보를 가져옴
            model.addAttribute("user", userName);
        }

        ChatRoomDTO room = new ChatRoomDTO();

        model.addAttribute("room", room);

        return "chatroom";
    }
/*
    // 유저 카운트
    @GetMapping("/chat/chkUserCnt/{chatRoomSeq}")
    @ResponseBody
    public boolean chUserCnt(@PathVariable String chatRoomSeq){

        return chatService.chkRoomUserCnt(chatRoomSeq);
    }

    // 채팅에 참여한 유저 리스트 반환
    @GetMapping("/chat/userlist")
    @ResponseBody
    public ArrayList<String> userList(String chatRoomSeq) {

        return chatService.getUserList(ChatRoomMap.getInstance().getChatRooms(), chatRoomSeq);
    }*/
}
