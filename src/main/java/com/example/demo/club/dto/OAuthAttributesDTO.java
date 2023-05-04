package com.example.demo.club.dto;

import com.example.demo.club.domain.Role;
import com.example.demo.club.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class OAuthAttributesDTO {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributesDTO(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    //사용자 정버는 Map이기 때문에 변경필요
    public static OAuthAttributesDTO of(String registrationId, String userNameAttributeName, Map<String,Object> attributes){
        //네이버 로그인인지 확인
        if("naver".equals(registrationId)) {
            return ofNaver("id",attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributesDTO ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        //응답 받은 사용자의 정보를 Map 형태로 변경
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        //미리 정의한 속성으로 빌드.
        return OAuthAttributesDTO.builder()
                .name(response.get("name").toString())
                .email(response.get("email").toString())
                .picture(response.get("profile_image").toString())
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributesDTO ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        //응답 받은 사용자의 정보를 Map 형태로 변경
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        //미리 정의한 속성으로 빌드.
        return OAuthAttributesDTO.builder()
                .name(response.get("name").toString())
                .email(response.get("email").toString())
                .picture(response.get("picture").toString())
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.ROLE_USER)
                .build();
    }


}
