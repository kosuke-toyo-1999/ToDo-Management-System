package com.dmm.task.Form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TaskForm {
	
	public String title;
	public String text;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public LocalDate date;

}
