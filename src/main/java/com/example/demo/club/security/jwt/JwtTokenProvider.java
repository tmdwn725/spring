package com.example.demo.club.security.jwt;

import com.example.demo.club.common.RedisUtil;
import com.example.demo.club.dto.TokenDTO;
import com.example.demo.club.exception.CustomException;
import com.example.demo.club.security.CustomUserDetailService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.accessSecretKey}")
    private String accessSecretKey;
    @Value("${jwt.refreshSecretKey}")
    private String refreshSecretKey;
    @Value("${jwt.accessTokenExpireTime}")
    private long accessTokenExpireTime;
    @Value("${jwt.refreshTokenExpireTime}")
    private long refreshTokenExpireTime;
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisUtil redisUtil;
    private final CustomUserDetailService userDetailService;

    /**
     * 적절한 설정을 통해 토큰을 생성하여 반환
     * @param authentication
     * @return
     */
    public TokenDTO generateToken(Authentication authentication) {
        Date now = new Date();

        //Refresh Token
        String refreshToken = doGenerateRefreshToken(authentication.getName());
        //Access Token
        String accessToken = doGenerateAccessToken(authentication.getName());

        TokenDTO token = new TokenDTO(accessToken,refreshToken);
        token.setExpireTime(now.getTime() + accessTokenExpireTime);
        return token;
    }

    /**
     * 적절한 설정을 통해 access토큰을 생성하여 반환
     * @param memberId
     * @return
     */
    public String doGenerateAccessToken(String memberId) {
        Date now =  new Date();
        Claims claims = Jwts.claims().setSubject(memberId);
        //Access Token
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenExpireTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    /**
     * http 헤더로부터 bearer 토큰을 가져옴.
     * @param request
     * @return
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 쿠키로 부터 JWT 토큰을 가져옴.
     * @param request
     * @return
     */
    public String getJwtTokenFromCookie(HttpServletRequest request, String type) {
        String token = Optional.ofNullable(request.getCookies())
                .map(Arrays::stream)
                .flatMap(stream -> stream.filter(c -> c.getName().equals(type)).findFirst())
                .map(Cookie::getValue)
                .orElse(null);
        return token;
    }

    /**
     * 토큰을 검증
     * @param token
     * @return
     */
    public boolean validateToken(String token, boolean accessYn) {
        String secretKey = accessSecretKey;
        if(!accessYn){
            secretKey = refreshSecretKey;
        }

        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    /**
     * 토큰으로부터 User 객체를 생성하여 Authentication 객체를 반환
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        String username = getSubjectFromAccessToken(token);
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Access토큰으로부터 클레임을 만들고 sub(memberId)를 반환
     * @param token
     * @return
     */
    public String getSubjectFromAccessToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(token);
        return claims.getBody().getSubject();
    }

    /**
     * Refresh토큰으로부터 클레임을 만들고 sub(memberId)를 반환
     * @param token
     * @return
     */
    public String getSubjectFromRefreshToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(token);
        return claims.getBody().getSubject();
    }

    // JWT refreshToken 생성
    public String doGenerateRefreshToken(String memberId) {
        Date now =  new Date();
        Claims claims = Jwts.claims().setSubject(memberId);
        return  Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + refreshTokenExpireTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    public boolean getBlackListCheck(String token){
        return redisUtil.hasKeyBlackList(token);
    }

    // JWT 토큰에서 expire time 값을 가져오는 메소드
    public long getExpirationDateFromToken(String token) {
        try {
            final Claims claims = Jwts.parser().parseClaimsJws(token).getBody();
            return claims.getExpiration().getTime();
        }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return 0;
    }


    // DB에서 JWT refreshToken 발급
    public String reGenerateRefreshToken(String memberId){
        try {
            // 토큰 생성
            String refreshToken = doGenerateRefreshToken(memberId);
            TokenDTO tokenDto = new TokenDTO(doGenerateAccessToken(memberId), refreshToken);

            // RefreshToken Redis에 업데이트
            redisTemplate.opsForValue().set(
                    memberId,
                    refreshToken,
                    tokenDto.getExpireTime(),
                    TimeUnit.MILLISECONDS
            );

            HttpHeaders httpHeaders = new HttpHeaders();

            return refreshToken;
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid refresh token supplied", HttpStatus.BAD_REQUEST);
        }
    }

    public String getRefreshToken(String memberId) {
        String refreshToken = redisUtil.getValues(memberId);
        return refreshToken;
    }
}
