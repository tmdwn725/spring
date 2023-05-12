package com.example.demo.club.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.club.common.ModelMapperUtil;
import com.example.demo.club.domain.ClubInfo;
import com.example.demo.club.domain.File;
import com.example.demo.club.domain.Member;
import com.example.demo.club.dto.FileDTO;
import com.example.demo.club.dto.MemberDTO;
import com.example.demo.club.repository.ClubInfoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.club.domain.Club;
import com.example.demo.club.dto.ClubDTO;
import com.example.demo.club.repository.ClubRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubService {

    private final ClubRepository clubRepository;

	private final ClubInfoRepository clubInfoRepository;
    
    private final ModelMapper modelMapper;

	public List<ClubDTO> selectClubList(){
		List<ClubDTO> selectClubList = ModelMapperUtil.mapAll(clubRepository.findNewClub(), ClubDTO.class);
		return selectClubList;
	}

	public ClubDTO selectClub(Long clubSeq) {
		Club club = clubRepository.findByClub(clubSeq);
		ClubDTO dto = modelMapper.map(club, ClubDTO.class);
		return dto;
	}

	@Transactional
	public void createClub(ClubDTO dto, MemberDTO dto2) {
		// 동아리 등록
		Club club = new Club();
		Member member = new Member();
		ClubInfo clubInfo = new ClubInfo();
		LocalDateTime currentDate = LocalDateTime.now();

		member.setMemberSeq(dto2.getMemberSeq());

		club.setClubNm(dto.getClubNm());
		club.setClubClsCd(dto.getClubClsCd());
		club.setIntroduce(dto.getIntroduce());
		club.setRoomNm(dto.getRoomNm());
		club.setSchoolCd("100101");
		club.setRegDt(currentDate);

		clubInfo.setMember(member);
		clubInfo.setClub(club);
		clubInfo.setPosition("동아리장");
		clubInfo.setJoinDate(currentDate);

		club.getClubInfoList().add(clubInfo);

		clubRepository.save(club);
	}

}
