package com.example.demo.club.common;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
}
