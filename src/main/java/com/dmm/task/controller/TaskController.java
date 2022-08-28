package com.dmm.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dmm.task.repository.TasksRepository;

@Controller
public class TaskController {

	@Autowired
	private TasksRepository task_Repo;

	// アノテーション付きのメソッド追加
	@RequestMapping("/")
	public String index() {
		return "index";
	}

	// Registの画面
	@RequestMapping("/create")
	public String create() {
		return "create";
	}

	// Editの画面
	@RequestMapping("/edit")
	public String test() {
		return "edit";
	}

	// To doの画面
	@RequestMapping("/main")
	public String main() {
		return "main";
	}

	// Sign inの画面
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
}
