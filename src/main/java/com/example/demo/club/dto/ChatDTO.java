package com.example.demo.club.dto;

import com.example.demo.club.domain.ClubInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
    private Long chatSeq;
    private String message; // 메시지
    private LocalDateTime sendDt; // 채팅 발송 시간
    private ClubInfoDTO clubInfo;
    // 메시지  타입 : 입장, 채팅
    // 메시지 타입에 따라서 동작하는 구조가 달라진다.
    // 입장과 퇴장 ENTER 과 LEAVE 의 경우 입장/퇴장 이벤트 처리가 실행되고,
    // TALK 는 말 그대로 내용이 해당 채팅방을 SUB 하고 있는 모든 클라이언트에게 전달된다.
    public enum MessageType{
        ENTER, TALK, LEAVE;
    }

    private Long clubSeq;
    private Long clubInfoSeq;
    private MessageType type; // 메시지 타입
    private String sender; // 채팅을 보낸 사람
    private String senderId;
    private Long memberSeq;
    private String messageId;

}
