package com.dmm.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Main {
	// To doの画面
	@RequestMapping("/")
	public String main() {
		return "main";
	}

}
