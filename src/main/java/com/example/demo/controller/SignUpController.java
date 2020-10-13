package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;
import com.example.demo.model.User.Role;
import com.example.demo.repo.UserRepo;

@Controller
@RequestMapping("/signup")
public class SignUpController {

	@Autowired
	private UserRepo repo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@GetMapping
	public String index() {
		return "signup";
	}
	
	@PostMapping
	public String signUp(
			@ModelAttribute("user") @Valid User user,
			BindingResult result) {
		if(result.hasErrors()) {
			return "signup";
		}
		user.setPassword(encoder.encode(user.getPassword()));
		repo.save(user);
		return "redirect:/member/home";
	}
	
	@ModelAttribute(name = "user")
	public User user() {
		User user = new User();
		user.setEnable(true);
		user.setRole(Role.ROLE_MEMBER);
		return user;
	}
}
