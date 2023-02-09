package com.example.demo.club.service;

import com.example.demo.club.domain.Member;
import com.example.demo.club.domain.Role;
import com.example.demo.club.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

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

}
