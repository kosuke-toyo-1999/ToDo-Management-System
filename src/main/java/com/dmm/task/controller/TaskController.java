package com.dmm.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dmm.task.repository.TasksRepository;

@Controller
public class TaskController {

	@Autowired
	private TasksRepository task_Repo;

}
