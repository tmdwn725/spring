package com.example.demo.club.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/club")
public class ClubController {
	
	@RequestMapping("/myClub")
	public String getClub(Model model) {
		return "club/club";
	}

}
