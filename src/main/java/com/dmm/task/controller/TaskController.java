package com.dmm.task.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String main(Model model, @AuthenticationPrincipal AccountUserDetails user) {
		

		    // ★DEBUG
		    List<Tasks> tasks_DEBUG = tasksRepository.findAll();
		    for(Tasks t : tasks_DEBUG) {

		        // 登録したタスクの情報が出力されていれば、set自体はできている
		        // 確認できたら、あとはカレンダー上にタスク情報を表示させるだけ
		        System.out.println(t);  
		    }
		

		// ① 2次元表になるので、ListのListを用意する
		List<List<LocalDate>> matrix = new ArrayList<>();
		System.out.println("35=" + matrix);

		// ② 1週間分のLocalDateを格納するListを用意する
		List<LocalDate> week = new ArrayList<>();
		System.out.println("39=" + week);
		// ③ その月の1日のLocalDateを取得する
		LocalDate day = LocalDate.now();
		System.out.println("42=" + day);
		day = LocalDate.of(day.getYear(), day.getMonthValue(), 1);
		System.out.println("44=" + day);
		// ④ 曜日を表すDayOfWeekを取得し
		DayOfWeek DayOfWeek = day.getDayOfWeek();
		System.out.println("47=" + DayOfWeek);

		// ④ 上で取得したLocalDateに曜日の値（DayOfWeek#getValue)をマイナスして前月分のLocalDateを求める

		day = day.minusDays(DayOfWeek.getValue());
		System.out.println(day);
		// ⑤ 1日ずつ増やしてLocalDateを求めていき、2．で作成したListへ格納していき、1週間分詰めたら1．のリストへ格納する

		for (int i = 1; i <= 7; i++) {
			System.out.println("56=" + day);
			System.out.println("57=" + week);
			// 1日ごとにdayをweekにaddしなければいけない
			week.add(day);
			// 1日増やす（dayをプラス1日する）
			day = day.plusDays(1);
		}
		matrix.add(week);
		System.out.println("64=" + matrix);
		// ⑥2週目以降は単純に1日ずつ日を増やしながらLocalDateを求めてListへ格納していき、土曜日になったら1．のリストへ格納して新しいListを生成する（月末を求めるにはLocalDate#lengthOfMonth()を使う）
		week = new ArrayList<>();

		System.out.println("68=" + week);
		System.out.println("69=" + day.lengthOfMonth());
		for (int i = 7; i <= day.lengthOfMonth(); i++) {
			System.out.println("71=" + day);
			week.add(day);
			System.out.println("73=" + day.getDayOfWeek().getValue());
			if (day.getDayOfWeek() == java.time.DayOfWeek.SATURDAY) {
				System.out.println("75=" + day.getDayOfWeek().getValue());
				matrix.add(week);
				System.out.println("77=" + week);

				// 次週のListを新規作成（newをするとまっさらな新しいListを作成できます）
				week = new ArrayList<>();
			}
			// 1日増やす（dayをプラス1日する）
			day = day.plusDays(1);
			System.out.println("84=" + day);
		}
		week.add(day);
		System.out.println("87=" + week);
		System.out.println("88=" + day.getDayOfWeek().getValue());
		// ⑦ 最終週の翌月分をDayOfWeekの値を使って計算し、

		for (int j = 0; j <= 7 - day.getDayOfWeek().getValue(); j++) {
			System.out.println("92=" + day);
			System.out.println("93=" + week);
			// 1日増やす（dayをプラス1日する）
			day = day.plusDays(1);
			week.add(day);

		}
		matrix.add(week);
		// ⑦ 6．で生成したリストへ格納し、最後に1．で生成したリストへ格納する
		// 次週のListを新規作成（newをするとまっさらな新しいListを作成できます）
		System.out.println("101=" + day);
		System.out.println("102=" + week);
		System.out.println("103=" + matrix);
		model.addAttribute("matrix", matrix);

		// 8. 管理者は全員分のタスクを見えるようにする

		// LocalDateとTasksとセットで持つ、MultiValueMapというものを使います。
		MultiValueMap<LocalDate, Tasks> tasks = new LinkedMultiValueMap<LocalDate, Tasks>();

		List<Tasks> list;

		if(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(a -> a.equals("ROLE_ADMIN"))) {
		    list = tasksRepository.findAllByDateBetween((day.minusDays(DayOfWeek.getValue()).atTime(0,0)), (day.atTime(0,0)),Tasks.getName());
		} else {
		    //list = 
		}
//
//		for(Tasks t : list) {
//		    tasks.add(t.getDate().toLocalDate(), t);
//		}
//		
		model.addAttribute("tasks", tasks);

		return "main";
	}

	@GetMapping("/")
	public String index() {
		return "login";
	}

	@GetMapping("/main/create/{date}")
	public String create(Model model, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

		TaskForm form = new TaskForm();

		model.addAttribute("Form", form);

		return "create";
	}

	@PostMapping("/main/create")
	public String registerTask(TaskForm TaskForm, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,@AuthenticationPrincipal AccountUserDetails user) {

		Tasks task = new Tasks();
		task.setTitle(TaskForm.getTitle());
		task.setText(TaskForm.getText());
		task.setDate(TaskForm.getDate());
		task.setName(user.getName());
		task.setDone(false);

		// データベースに保存
		tasksRepository.save(task);
		return "redirect:/main";
	}

	@PostMapping("/main/edit/{id}")
	public String main_edit_id(TaskForm TaskForm,Model model, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,@AuthenticationPrincipal AccountUserDetails user) {
		TaskForm form = new TaskForm();
		model.addAttribute("Form", form);
		// データベースに保存
		model.addAttribute("Form", form);
		
		Tasks task = new Tasks();
		task.setTitle(TaskForm.getTitle());
		task.setText(TaskForm.getText());
		task.setDate(TaskForm.getDate());
		task.setName(user.getName());
		task.setDone(TaskForm.isDone());

		// データベースに保存
		tasksRepository.save(task);
		
		return "redirect:/main";
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
