package com.example.demo.club.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "code_group")
public class CdGrp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="grp_cd")
    private String grpCd;

    @Column(name = "grp_cd_nm")
    private String grpCdNm;

    public void createCdGrp(String grpCd, String grpCdNm){
        this.grpCd = grpCd;
        this.grpCdNm = grpCdNm;
    }
}
