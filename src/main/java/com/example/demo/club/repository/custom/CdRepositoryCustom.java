package com.example.demo.club.repository.custom;

import com.example.demo.club.domain.Cd;

import java.util.List;

public interface CdRepositoryCustom {
    List<Cd> selectCdList(String grpCd);
}
