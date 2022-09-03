package com.dmm.task.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Tasks {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	public int id;
	public String title;
	public String name;
	public String text;
	public LocalDateTime date;
	public boolean done;

}
