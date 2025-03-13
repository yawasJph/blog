package com.yakuash.blog.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yakuash.blog.entity.UserEntity;
import com.yakuash.blog.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller("/")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/record")
	public String record() {
		return "/user/register";
	}
	
	@PostMapping("/register")
	public String register(UserEntity user,RedirectAttributes ra) {	
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.createUser(user);
		ra.addFlashAttribute("success", "Register Successfully");
		return "redirect:/record";
	}
	
	@GetMapping(value = {"/login","/"})
	public String login() {
		return "/user/login";
	}
	
	@GetMapping("/access")
    public String access(HttpSession session) {
        Optional<UserEntity> optionalUser = userService.getUserById(Long.parseLong(session.getAttribute("user_session_id").toString()));
        if (optionalUser.isPresent()) {
            session.setAttribute("user_session_id", optionalUser.get().getId());
            return "redirect:/post/home";
        } else {
            return "redirect:/login-page"; 
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,RedirectAttributes ra) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        ra.addFlashAttribute("logout", "Logout Successfully");
        return "redirect:/login";
    }
}
