package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@GetMapping
	public String index(HttpServletRequest request) {
		
		if(request.isUserInRole("MEMBER")) {
			return "/member/home";
		}else if(request.isUserInRole("ADMIN")) {
			return"/admin/home";
		}
		return "index";
	}

}
