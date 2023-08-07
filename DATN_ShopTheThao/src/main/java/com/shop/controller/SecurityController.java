package com.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.util.AuthenticationFacade;

@Controller
public class SecurityController {
	
	@Autowired
    private AuthenticationFacade authenticationFacade;
	
	@RequestMapping("/account/login/form")
	public String loginForm(Model model) {
		return "/account/login";
	}

	@RequestMapping("/account/login/success")
	public String loginSuccess(Model model) {
		System.out.println(authenticationFacade.getUsername());
		if(authenticationFacade.hasRole("ROLE_user")) {
			System.out.println("1");
			return "redirect:/home";
		}
		System.out.println("2");
		return "/admin-page/dashboard";
	}
	
	@RequestMapping("/account/login/error")
	public String loginError(Model model) {
		return "/account/login";
	}
	
	@RequestMapping("/account/unauthoried")
	public String unauthoried(Model model) {
		return "/account/login";
	}
	
	@RequestMapping("/account/logoff/success")
	public String logoffSuccess(Model model) {
		SecurityContextHolder.clearContext();
		return "redirect:/home";
	}
	
	
	
}