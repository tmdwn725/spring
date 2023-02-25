package com.example.demo.club.controller;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.club.common.EntityConvert;
import com.example.demo.club.dto.ClubDTO;
import com.example.demo.club.service.ClubService;

@Controller
@RequestMapping("/club")
public class ClubController {
	
	@Autowired
	ClubService clubService;
	
	@RequestMapping("/myClub")
	public String getClub(Model model) {
		//ClubDTO dto = (ClubDTO) EntityConvert.convert(clubService.selectClub((long) 1));
		return "club/club";
	}

}
