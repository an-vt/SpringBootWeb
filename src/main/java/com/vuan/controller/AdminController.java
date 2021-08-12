package com.vuan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
	
	@GetMapping(value = "/admin")
	public String AdminIndexGet() {
		return "templates/admin/index";
	}
	
	@GetMapping(value = "/login-admin")
	public String AdminLoginGet(Model model ,@RequestParam(name = "e" ,required = false) String error) {
		System.out.println("method login called");
		if(error != null){ 
			System.out.println("Co loi xay ra");
			model.addAttribute("error", error);
		}
		return "admin/login";
	}
	
}
