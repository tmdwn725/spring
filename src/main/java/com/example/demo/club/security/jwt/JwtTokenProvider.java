package com.example.demo.club.security.jwt;

import com.example.demo.club.common.RedisUtil;
import com.example.demo.club.domain.RefreshToken;
import com.example.demo.club.dto.TokenDTO;
import com.example.demo.club.exception.CustomException;
import com.example.demo.club.repository.RefreshTokenRepository;
import com.example.demo.club.security.CustomUserDetailService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

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
    @Autowired
    private CustomUserDetailService userDetailService;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    /**
     * 적절한 설정을 통해 토큰을 생성하여 반환
     * @param authentication
     * @return
     */
    public TokenDTO generateToken(Authentication authentication) {
        Date now = new Date();

        //Refresh Token
        String refreshToken = reGenerateRefreshToken(authentication.getName());
        //Access Token
        String accessToken = doGenerateAccessToken(authentication.getName());

        TokenDTO token = new TokenDTO(accessToken,refreshToken);
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setExpireTime(now.getTime() + accessTokenExpireTime);
        return token;
    }

    // JWT accessToken 생성
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


    /**
     * 토큰으로부터 클레임을 만들고, 이를 통해 User 객체를 생성하여 Authentication 객체를 반환
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        String username = Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(token).getBody().getSubject();
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * http 헤더로부터 bearer 토큰을 가져옴.
     * @param req
     * @return
     */
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
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
            /*if (redisUtil.hasKeyBlackList(token)){
                // TODO 에러 발생시키는 부분 수정
                throw new RuntimeException("로그아웃 했지롱~~");
            }*/
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


    // DB에서 JWT refreshToken 발급
    public String reGenerateRefreshToken(String memberId){
        try {
            /*String refreshToken = redisUtil.get(memberId);
            // Refresh Token 검증
            if (!validateToken(refreshToken,false) {
                throw new CustomException("Invalid refresh token supplied", HttpStatus.BAD_REQUEST);
            }*/
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

        /*
        log.info("refreshToken DB 조회");
        // DB에서 refreshToken 정보 조회
        RefreshToken token = refreshTokenRepository.findTokenByMemberIdId(memberId);

        // 디비에 토큰이 존재하지 않음
        if(token == null) {
            log.info("[reGenerateRefreshToken] refreshToken 정보가 존재하지 않습니다.");
            return saveRefreshToken(memberId);
        }

       try {
            String refreshToken = token.getToken().substring(7);
            Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(refreshToken);
            log.info("[reGenerateRefreshToken] refreshToken이 만료되지 않았습니다.");
            return refreshToken;
        }catch(ExpiredJwtException e) {
            log.info("[reGenerateRefreshToken] refreshToken이 만료되었습니다.");
            // 토큰 삭제 후 저장
            removeRefreshToken(token);
            return saveRefreshToken(memberId);
        } catch(Exception e) {
            // 그 외 예외처리
            log.error("[reGenerateRefreshToken] refreshToken 재발급 중 문제 발생 : {}", e.getMessage());
            return null;
        }*/
    }
    
    // refresh 토큰 삭제
    private void removeRefreshToken(RefreshToken token){
        refreshTokenRepository.delete(token);
    }

    // refresh 토큰 저장
    private String saveRefreshToken(String memberId){
        RefreshToken token = new RefreshToken();
        Date now = new Date();
        LocalDateTime expiryTime = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault())
                .plus(Duration.ofMillis(refreshTokenExpireTime));
        String refreshToken =  doGenerateRefreshToken(memberId);
        token.setUseYn("Y");
        token.setMemberId(memberId);
        token.setExpireTime(expiryTime);
        token.setToken("Bearer " + refreshToken);
        refreshTokenRepository.save(token);
        log.info("refreshToken 재발급 완료 : {}", "Bearer " + refreshToken);
        return refreshToken;
    }

    // token으로 사용자 id 조회
    public String getMemberIdFromToken(String token) {
        return Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getJwtTokenFromCookie(HttpServletRequest request, String type) {
        String token = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(type))
                .findFirst() .map(Cookie::getValue)
                .orElse(null);
        return token;
    }

    public String getRefreshToken(String memberId) {
        String refreshToken = redisUtil.getValues(memberId);
        return refreshToken;
    }
}
