package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;
import com.example.demo.model.User.Role;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/admin/member")
public class AdminController {

	@Autowired
	public UserService service;

	@GetMapping()
	public String index(ModelMap model) {
		model.put("members", service.findAll());
		return "/admin/ad_member";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id) {
		return "/admin/edit-member";
	}

	@PostMapping("/edit/{id}")
	public String edit(@PathVariable Long id, @ModelAttribute(name = "member") @Valid User member,
			BindingResult result) {
		if (result.hasErrors()) {
			return "admin/edit-member";
		}
		service.update(member);
		return "redirect:/admin/member";
	}

	@ModelAttribute("roles")
	public List<Role> roles() {
		return Arrays.asList(Role.values());
	}

	@ModelAttribute("member")
	public User member(@PathVariable(required = false) Long id) {
		if (id != null) {
			User user = service.findById(id);
			return user;
		}
		return null;
	}
}