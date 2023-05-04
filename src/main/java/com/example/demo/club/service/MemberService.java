package com.example.demo.club.service;

import java.util.List;

import com.example.demo.club.common.CookieUtil;
import com.example.demo.club.common.RedisUtil;
import com.example.demo.club.dto.TokenDTO;
import com.example.demo.club.exception.CustomException;
import com.example.demo.club.security.jwt.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.club.domain.Member;
import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RedisUtil redisUtil;
    private final CookieUtil cookieUtil;

    /** 생성 **/
    /* 회원 생성 */
    @Transactional
    public void createMember(Member member) {
        memberRepository.save(member);
    }

    /** 조회 **/
    /* 전체 회원 조회 */
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    /* 회원 조회 */
    public Member findOne(String memberId) {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("아이디를 확인해주세요."));
    }

    public MemberDTO selectMemberById(String id) {
        MemberDTO dto = modelMapper.map(memberRepository.fingByMemberId(id), MemberDTO.class);
        return dto;
    }

    public MemberDTO selectMemberBySeq(Long memberSeq) {
        MemberDTO dto = modelMapper.map(memberRepository.findById(memberSeq).orElse(null), MemberDTO.class);
        return dto;
    }
    
    /**  
     * 로그인
     */
    public ResponseEntity<TokenDTO> signIn(MemberDTO member, HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            member.getMemberId(),
                            member.getPassword()
                    )
            );

            String accessToken = jwtTokenProvider.getJwtTokenFromCookie(request, "accessToken");
            //  이전 블랙리스트 삭제
            if (redisUtil.hasKeyBlackList(accessToken)){
                // Refresh Token을 삭제
                redisUtil.deleteBlackList(accessToken);
            }

            TokenDTO tokenDto = jwtTokenProvider.generateToken(authentication);
            redisUtil.setValues(authentication.getName(), tokenDto.getRefreshToken(), tokenDto.getExpireTime());

            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new CustomException("입력하신 정보와 일치한 사용자가 없습니다", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    
    /**
     * 로그아웃
     */
    public void signout(HttpServletRequest request, HttpServletResponse response, String memberId) {
        String accessToken = jwtTokenProvider.getJwtTokenFromCookie(request, "accessToken");
        // 해당 Access Token 유효시간을 가지고 와서 BlackList에 저장하기
        long expiration = jwtTokenProvider.getExpirationDateFromToken(accessToken);
        redisUtil.setBlackList(accessToken,"logout");

        // Redis에서 해당 User email로 저장된 Refresh Token 이 있는지 여부를 확인 후에 있을 경우 삭제를 한다.
        if (redisUtil.getValues(memberId)!=null){
            // Refresh Token을 삭제
            redisUtil.deleteValues(memberId);
        }

        cookieUtil.expiringCookie(request,response,"refreshToken");

    }
}
