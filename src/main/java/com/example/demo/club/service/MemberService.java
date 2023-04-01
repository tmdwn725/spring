package com.example.demo.club.service;

import java.util.List;

import com.example.demo.club.component.JwtTokenProvider;
import com.example.demo.club.dto.TokenInfo;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
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

    @Transactional
    public TokenInfo login(String memberId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }


    public MemberDTO selectMemberById(String id) {
    	MemberDTO dto = modelMapper.map(memberRepository.fingByMemberId(id), MemberDTO.class);
		return dto;
	}

    public MemberDTO selectMemberBySeq(Long memberSeq) {
    	MemberDTO dto = modelMapper.map(memberRepository.findById(memberSeq).orElse(null), MemberDTO.class);
    	return dto;
    }

}
