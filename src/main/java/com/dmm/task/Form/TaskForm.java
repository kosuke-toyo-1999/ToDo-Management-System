package com.dmm.task.Form;

import java.time.LocalDate;

import javax.persistence.Id;

import lombok.Data;

@Data
public class TaskForm {

	@Id
	public int id;
	public String title;
	public String name;
	public String text;
	public LocalDate date;
	public boolean done;


}
