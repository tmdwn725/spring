package com.example.demo.club.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @Column(name ="member_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberSeq;

    @Column(name = "member_id")
    private String memberId;
    
    @Column(name = "member_nm")
    private String memberNm;

    @Column(name = "password")
    private String password;

    @Column(name = "authority")
    private String authority;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @OneToMany(mappedBy = "member")
	private List<ClubInfo> clubInfoList = new ArrayList<>();

    public void createMember(String memberId, String memberNm, String password, String authority, Role role){
        this.memberId = memberId;
        this.memberNm = memberNm;
        this.password = password;
        this.authority = authority;
        this.role = role;
    }

}