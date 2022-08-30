package com.dmm.task.controller;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import com.dmm.task.Form.TaskForm;
import com.dmm.task.entity.Tasks;
import com.dmm.task.repository.TasksRepository;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class TaskController {

	@Autowired
	private TasksRepository tasks_repo;

	@GetMapping("main")
	public String main(Model model) {
		// ログイン画面
		// カレンダーの作成
		
		// 実行時の日付/時刻情報を持つカレンダーインスタンス作成(ex 2021/01/08 22:00:00)
		Calendar cal = Calendar.getInstance();
		// インタンスの持つ日付情報を1日に変更(ex 2021/01/01 22:00:00)
		cal.set(Calendar.DATE, 1);
		// DAY_OF_WEEKでその日の曜日を返す2021/01/01は金曜なので6
		// （日曜:1,月:2,火:3,,,,土:7）
		// カレンダー的な最初のブランクの数は以下の式で表せる(1日が金なら空白は日、月、火、水、木の５個)
		int beforeBlank = cal.get(Calendar.DAY_OF_WEEK) - 1;
		// その月が何日まであるかは以下のメソッドで求められる(1月は31日)
		int daysCount = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// ブランクと日数分ループを回す
		for (int i = 0; i < beforeBlank + daysCount; i++) {
			String str = "";// ブランクは空文字
			// 最初のブランク分すぎたら日付
			if (i >= beforeBlank) {
				// カウンター変数iから求める実際の日付
				int date = i + 1 - beforeBlank;
				str = String.valueOf(date);
			}
		}

		List<Tasks> list = tasks_repo.findAll();
		model.addAttribute("tasks", list);
		TaskForm taskForm = new TaskForm();
		model.addAttribute("taskForm", taskForm);
		return "main";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/logout")
	public String logout() {
		return "create";
	}

	@GetMapping("/")
	public String index() {
		return "login";
	}

	@GetMapping("/main/create")
	public String create(@Validated TaskForm taskForm, BindingResult bindingResult,
			@AuthenticationPrincipal AccountUserDetails user, Model model) {

		if (bindingResult.hasErrors()) {
			// エラーがある場合は投稿登録画面を返す
			List<Tasks> list = tasks_repo.findAll(Collections.sort("date"));
			model.addAttribute("tasks", list);
			model.addAttribute("taskForm", taskForm);
			return "/main/create";
		}

		Tasks task = new Tasks();
		task.setName(user.getName());
		task.setTitle(task.getTitle());
		task.setText(task.getText());
		task.setDate(LocalDateTime.now());

		tasks_repo.save(task);

		return "redirect:";
	}

	@GetMapping("/main/edit/{id}")
	public String main__edit_id() {
		return "redirect:";
	}

}
