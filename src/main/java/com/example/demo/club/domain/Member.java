package com.example.demo.club.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Table(name = "member")
public class Member {

    @Id
    @Column(name ="member_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberSeq;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "password")
    private String password;

    @Column(name = "authority")
    private String authority;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_seq")
    private Club club;

    public void createMember(String memberId, String password, String authority, Role role){
        this.memberId = memberId;
        this.password = password;
        this.authority = authority;
        this.role = role;
    }

}