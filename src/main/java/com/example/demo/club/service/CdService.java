package com.example.demo.club.service;

import com.example.demo.club.common.ModelMapperUtil;
import com.example.demo.club.domain.Cd;
import com.example.demo.club.dto.CdDTO;
import com.example.demo.club.repository.CdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CdService {
    private final CdRepository cdRepository;

    @Cacheable(value = "clubCd")
    public List<CdDTO> getClubCd(){
        List<CdDTO> clubCdList = ModelMapperUtil.mapAll(cdRepository.selectCdList("2001"),CdDTO.class);
        return clubCdList;
    }
}
