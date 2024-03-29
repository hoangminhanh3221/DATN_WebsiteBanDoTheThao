package com.shop.controller.user;

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
	
	@RequestMapping("/account/login/email")
	public String loginEmail(Model model) {
		model.addAttribute("mailMessage", "Vui lòng kiểm tra email!");
		return "/account/login";
	}
	
	@RequestMapping("/account/forgot-password")
	public String forgotPass(Model model) {
		return "/account/forgot-password";
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
		model.addAttribute("message", "Tên đăng nhập hoặc mật khẩu không chính xác!");
		return "/account/login";
	}
	
	@RequestMapping("/account/unauthoried")
	public String unauthoried(Model model) {
		model.addAttribute("message", "Không có quyền truy cập!");
		return "/account/login";
	}
	
	@RequestMapping("/account/logoff/success")
	public String logoffSuccess(Model model) {
		SecurityContextHolder.clearContext();
		return "redirect:/home";
	}
	
	@RequestMapping("/account/register")
	public String accRegister(Model model) {
		return "/account/register";
	}
	
}