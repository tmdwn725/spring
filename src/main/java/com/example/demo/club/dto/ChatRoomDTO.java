package com.example.demo.club.dto;

import com.example.demo.club.domain.Club;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
    private Long chatRoomSeq;
    private ClubDTO club;
    private MemberDTO member;
    private List<ChatDTO> chatList;

    private String roomId;
    @Builder.Default
    private int userCount = 0;
    private int maxUserCnt;
    public enum ChatType{  // 화상 채팅, 문자 채팅
        MSG, RTC
    }
    private ChatType chatType; //  채팅 타입 여부

    // ChatRoomDto 클래스는 하나로 가되 서비스를 나누었음
    @Builder.Default
    public ConcurrentMap<String, String> userList = new ConcurrentHashMap<String,String>();

}
