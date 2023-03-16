package com.example.demo.club.controller;

import com.example.demo.club.dto.ChatRoomDTO;
import com.example.demo.club.dto.ChatRoomMap;
import com.example.demo.club.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private  final ChatService chatService;

    // 채팅방 생성
    // 채팅방 생성 후 다시 / 로 return
    @PostMapping("/chat/createroom")
    public String createRoom(@RequestParam("roomName") String name,
                             @RequestParam("roomPwd") String roomPwd,
                             @RequestParam("secretChk") String secretChk,
                             @RequestParam(value = "maxUserCnt", defaultValue = "2") String maxUserCnt,
                             @RequestParam("chatType") String chatType,
                             RedirectAttributes rttr) {

        // log.info("chk {}", secretChk);

        // 매개변수 : 방 이름, 패스워드, 방 잠금 여부, 방 인원수
        ChatRoomDTO room;

        room = chatService.createChatRoom(name, roomPwd, Boolean.parseBoolean(secretChk), Integer.parseInt(maxUserCnt), chatType);


        log.info("CREATE Chat Room [{}]", room);

        rttr.addFlashAttribute("roomName", room);
        return "redirect:/";
    }

    // 채팅방 입장 화면
    // 파라미터로 넘어오는 roomId 를 확인후 해당 roomId 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.
    @GetMapping("/chat/room")
    public String roomDetail(Model model, String roomId){

        log.info("roomId {}", roomId);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        // principalDetails 가 null 이 아니라면 로그인 된 상태!!
        if (userName != null) {
            // 세션에서 로그인 유저 정보를 가져옴
            model.addAttribute("user", userName);
        }

        ChatRoomDTO room = ChatRoomMap.getInstance().getChatRooms().get(roomId);

        model.addAttribute("room", room);

        return "chatroom";
        /*
        if (ChatRoomDTO.ChatType.MSG.equals(room.getChatType())) {
            return "chatroom";
        }else {
            String uuid = UUID.randomUUID().toString().split("-")[0];
            model.addAttribute("uuid", uuid);
            model.addAttribute("roomId", room.getRoomId());
            model.addAttribute("roomName", room.getRoomName());
//            return "rtcroom";

            return "kurentoroom";
        }*/
    }

    // 유저 카운트
    @GetMapping("/chat/chkUserCnt/{roomId}")
    @ResponseBody
    public boolean chUserCnt(@PathVariable String roomId){

        return chatService.chkRoomUserCnt(roomId);
    }
}
