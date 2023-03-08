package com.example.demo.club.service;

import com.example.demo.club.common.ModelMapperUtil;
import com.example.demo.club.domain.Member;
import com.example.demo.club.domain.Role;
import com.example.demo.club.dto.ClubDTO;
import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.repository.ClubRepository;
import com.example.demo.club.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
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
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

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

}
