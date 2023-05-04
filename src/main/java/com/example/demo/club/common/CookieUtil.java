package com.example.demo.club.common;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Component
public class CookieUtil {
    public Cookie createCookie(String cookieName, String value){
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int)Integer.MAX_VALUE);
        cookie.setPath("/");
        //cookie.setSecure(true); //https
        return cookie;
    }

    public Cookie getCookie(HttpServletRequest request, String cookieName){
        Cookie[] cookies = request.getCookies();
        if(cookies==null) return null;
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName)){
                return cookie;
            }
        }
        return null;
    }

    public void expiringCookie(HttpServletRequest request, HttpServletResponse response, String cookieName){
        Optional.ofNullable(request.getCookies())
                .ifPresent(cookieArr -> Arrays.stream(cookieArr)
                        .filter(cookie -> cookie.getName().equals(cookieName))
                        .findFirst()
                        .ifPresent(cookie -> {
                            cookie.setMaxAge(0); // 만료시키기
                            response.addCookie(cookie); // 응답에 추가하기
                        }));
    }
}
