package com.example.demo.club.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.example.demo.club.common.RedisUtil;
import com.example.demo.club.dto.TokenDTO;
import com.example.demo.club.exception.CustomException;
import com.example.demo.club.repository.RefreshTokenRepository;
import com.example.demo.club.security.jwt.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    //private final RefreshTokenRepository refreshTokenRepository;
    private final RedisTemplate<String, String> redisTemplate;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(username)
                .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 사용자 입니다."));

        return User.builder()
                .username(member.getMemberId())
                .password(member.getPassword())
                .roles(member.getRole().name())
                .build();
    }

    public MemberDTO selectMemberById(String id) {
    	MemberDTO dto = modelMapper.map(memberRepository.fingByMemberId(id), MemberDTO.class);
		return dto;
	}

    public MemberDTO selectMemberBySeq(Long memberSeq) {
    	MemberDTO dto = modelMapper.map(memberRepository.findById(memberSeq).orElse(null), MemberDTO.class);
    	return dto;
    }

    public ResponseEntity<TokenDTO> signIn(MemberDTO member) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            member.getMemberId(),
                            member.getPassword()
                    )
            );

            TokenDTO tokenDto = jwtTokenProvider.generateToken(authentication);

            redisTemplate.opsForValue().set(
                    authentication.getName(),
                    tokenDto.getRefreshToken(),
                    tokenDto.getExpireTime(),
                    TimeUnit.MILLISECONDS
            );

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + tokenDto.getAccessToken());

            return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid credentials supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String logout(String accessToken, MemberDTO member) {

        //Long findUserId = jwtTokenProvider.getUserIdToToken(accessToken);

        //엑세스 토큰 남은 유효시간
        //Long expiration = jwtTokenProvider.getExpiration(accessToken);

        //Redis Cache에 저장
        //redisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);

        //리프레쉬 토큰 삭제
        //refreshTokenRepository.delete(findUserId);

        return "로그아웃 완료";
    }

}
