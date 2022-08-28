package com.dmm.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Edit {
	// Editの画面
	@RequestMapping("/edit")
	public String test() {
		return "edit";
	}

}
