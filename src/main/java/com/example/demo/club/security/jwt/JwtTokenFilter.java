package com.example.demo.club.security.jwt;

import com.example.demo.club.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
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
public class JwtTokenFilter extends OncePerRequestFilter {
    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);
        String refreshToken = null;
        //if (token == null && "/member/main".equals(request.getRequestURI())){
        if (token == null && !"/login".equals(request.getRequestURI())){
            token = jwtTokenProvider.getJwtTokenFromCookie(request,"accessToken");
            refreshToken = jwtTokenProvider.getJwtTokenFromCookie(request,"refreshToken");
        }

        try {
            if (token != null) {
                Authentication auth = null;
                String memberId = jwtTokenProvider.getMemberIdFromToken(refreshToken);
                if (jwtTokenProvider.validateToken(token,true)){
                    auth = jwtTokenProvider.getAuthentication(token);
                } else if(jwtTokenProvider.validateToken(refreshToken,false)){
                    token = jwtTokenProvider.doGenerateAccessToken(memberId);
                    auth = jwtTokenProvider.getAuthentication(token);
                }else{
                    refreshToken = jwtTokenProvider.reGenerateRefreshToken(memberId);
                    token = jwtTokenProvider.doGenerateAccessToken(memberId);
                    auth = jwtTokenProvider.getAuthentication(token);
                }

                // 정상 토큰이면 토큰을 통해 생성한 Authentication 객체를 SecurityContext에 저장
                SecurityContextHolder.getContext().setAuthentication(auth);
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