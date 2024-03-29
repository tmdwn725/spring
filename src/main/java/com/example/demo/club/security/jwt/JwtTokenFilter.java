package com.example.demo.club.security.jwt;

import com.example.demo.club.common.RedisUtil;
import com.example.demo.club.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

// Request 이전에 1회 작동할 필터
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtTokenProvider.getJwtTokenFromCookie(request,"accessToken");
            String refreshToken = null;

            if(token != null){
                refreshToken = jwtTokenProvider.getJwtTokenFromCookie(request,"refreshToken");
                Authentication auth = null;
                String memberId = "";
                if(!jwtTokenProvider.getBlackListCheck(token)){ // access 토큰 BlackList 체크
                    if (jwtTokenProvider.validateToken(token,true)){ // access 토큰 인증
                        auth = jwtTokenProvider.getAuthentication(token);
                        // 정상 토큰이면 토큰을 통해 생성한 Authentication 객체를 SecurityContext에 저장
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    } else if( jwtTokenProvider.validateToken(refreshToken,false)){  // refresh 토큰 인증
                        memberId = jwtTokenProvider.getSubjectFromRefreshToken(refreshToken);
                        // redis에서 받아온 토큰
                        String redisToken = jwtTokenProvider.getRefreshToken(memberId);
                        // redis에 존재하는 토큰과 비교하여 같으면 재발급
                        if(refreshToken.equals(redisToken)){
                            token = jwtTokenProvider.doGenerateAccessToken(memberId);
                            auth = jwtTokenProvider.getAuthentication(token);
                            // 정상 토큰이면 토큰을 통해 생성한 Authentication 객체를 SecurityContext에 저장
                            SecurityContextHolder.getContext().setAuthentication(auth);
                        }
                    }
                }
            }

        } catch (CustomException e) {
            SecurityContextHolder.clearContext();
            response.sendError(e.getHttpStatus().value(), e.getMessage());
            return;
        } catch (Exception e) {
            log.error("[JwtRequestFilter] refreshToken 재발급 체크 중 문제 발생 : {}", e.getMessage());
        }

        filterChain.doFilter(request, response); // 다음 필터 체인 실행
    }
}