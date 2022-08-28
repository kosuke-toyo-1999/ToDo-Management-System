package com.dmm.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Login {
	// Sign inの画面
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

}
