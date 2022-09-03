package com.dmm.task.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.Form.TaskForm;
import com.dmm.task.entity.Tasks;
import com.dmm.task.repository.TasksRepository;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class TaskController {

	@Autowired
	private TasksRepository tasksRepository;

	
	@GetMapping("/main")
	public String main(Model model) {
		


		// ① 2次元表になるので、ListのListを用意する
		List<List<LocalDate>> matrix = new ArrayList<>();

		// ② 1週間分のLocalDateを格納するListを用意する
		List<LocalDate> week = new ArrayList<>();

		// ③ その月の1日のLocalDateを取得する
		LocalDate day = LocalDate.now();
		day = LocalDate.of(day.getYear(), day.getMonthValue(), 1);

		// ④ 曜日を表すDayOfWeekを取得し
		DayOfWeek DayOfWeek = day.getDayOfWeek();

		// ④ 上で取得したLocalDateに曜日の値（DayOfWeek#getValue)をマイナスして前月分のLocalDateを求める

		day = day.minusDays(DayOfWeek.getValue());

		// ⑤ 1日ずつ増やしてLocalDateを求めていき、2．で作成したListへ格納していき、1週間分詰めたら1．のリストへ格納する

		for (int i = 1; i <= 7; i++) {
			// 1日ごとにdayをweekにaddしなければいけない
			week.add(day);
			// 1日増やす（dayをプラス1日する）
			day.plusDays(1);
		}
		matrix.add(week);

		// ⑥2週目以降は単純に1日ずつ日を増やしながらLocalDateを求めてListへ格納していき、土曜日になったら1．のリストへ格納して新しいListを生成する（月末を求めるにはLocalDate#lengthOfMonth()を使う）

		for (int i = 7; i <= day.lengthOfMonth(); i++) {

			if (day.getDayOfWeek() == java.time.DayOfWeek.SATURDAY) {
				matrix.add(week);

				// 次週のListを新規作成（newをするとまっさらな新しいListを作成できます）
				week = new ArrayList<>();
			}
			// 1日増やす（dayをプラス1日する）
			day = day.plusDays(1);
		}

		// ⑦ 最終週の翌月分をDayOfWeekの値を使って計算し、
		week = new ArrayList<>();
		for (int j = 1; j <= 7 - day.getDayOfWeek().getValue(); j++) {
			// 1日増やす（dayをプラス1日する）
			day = day.plusDays(1);
			week.add(day);
		}
		// ⑦ 6．で生成したリストへ格納し、最後に1．で生成したリストへ格納する
		// 次週のListを新規作成（newをするとまっさらな新しいListを作成できます）
		matrix.add(week);
		model.addAttribute("matrix", matrix);

		// 8. 管理者は全員分のタスクを見えるようにする

		// LocalDateとTasksとセットで持つ、MultiValueMapというものを使います。
//		MultiValueMap<LocalDate, Tasks> tasks = new LinkedMultiValueMap<LocalDate, Tasks>();
//
//		List<Tasks> list;
//
//		// user.getAuthorities() を使って、いまログインしているユーザーがADMINかどうかを判定
//		if (ADMINだったら) {
//		    list = Repositoryから全部取得
//		} else {
//		    list = Repositoryから当該ユーザーのものだけ取得
//		}
//
//		for(Tasks t : list) {
//		    tasks.add(t.getDate().toLocalDate(), t);
//		}
//
//		model.addAttribute("tasks", tasks);　//
		
		List<Tasks> list = tasksRepository.findAll();
		model.addAttribute("tasks", list);
		model.addAttribute("tasks.get(day)", list);
//		TaskForm taskForm = new TaskForm();
//		model.addAttribute("taskForm", taskForm);
		
//		Tasks task = new Tasks();
//		task.setName(task.getName());
//		task.setTitle(task.getTitle());
//		task.setText(task.getText());
//		task.setDate(LocalDateTime.now());
//
//		tasksRepository.save(task);

		return "main";
	}

	@GetMapping("/")
	public String index() {
		return "login";
	}

	@PostMapping("/main/create")
	public String create(@Validated TaskForm taskForm, BindingResult bindingResult,
			@AuthenticationPrincipal AccountUserDetails user, Model model) {

//		if (bindingResult.hasErrors()) {
//			// エラーがある場合は投稿登録画面を返す
//			List<Tasks> list = tasks_repo.findAll();
//			model.addAttribute("tasks", list);
//			model.addAttribute("taskForm", taskForm);
//			return "/main/create";
//		}



		return "/main/create";
	}

	@GetMapping("/main/edit/{id}")
	public String main_edit_id() {
		return "/main/create";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/logout")
	public String logout() {
		return "login";
	}
}
