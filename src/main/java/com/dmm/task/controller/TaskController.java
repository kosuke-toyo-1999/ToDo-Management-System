package com.dmm.task.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TaskController {

	@GetMapping("/userPage")
	@PreAuthorize("hasRole('ROLE_USER')") // 追記 ROLE_USERのユーザのみアクセスを許可
	public String userPage() {
		return "userPage";
	}

	// Editの画面
	@RequestMapping("/edit")
	public String test() {
		return "edit";
	}

	@GetMapping("/adminPage")
	@PreAuthorize("hasRole('ROLE_ADMIN')") // 追記 ROLE_ADMINのユーザのみアクセスを許可
	public String adminPage() {
		return "adminPage";
	}

	// To doの画面
	@RequestMapping("/")
	public String main() {
		return "main";
	}

	// Sign inの画面
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	// Registの画面
	@RequestMapping("/create")
	public String create() {
		return "create";
	}

}
