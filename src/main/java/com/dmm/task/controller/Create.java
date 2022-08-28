package com.dmm.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Create {
	// Registの画面
	@RequestMapping("/create")
	public String create() {
		return "create";
	}

}
