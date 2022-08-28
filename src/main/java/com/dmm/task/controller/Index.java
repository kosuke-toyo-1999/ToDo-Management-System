package com.dmm.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Index {
	// アノテーション付きのメソッド追加
	@RequestMapping("/")
	public String index() {
		return "index";
	}

}
