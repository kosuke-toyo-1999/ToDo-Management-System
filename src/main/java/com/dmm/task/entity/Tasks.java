package com.dmm.task.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = "password") // 自動生成されるtoStringにpasswordを出力しない

public class Tasks {
	@Id
	public int id;
	public String title;
	public String name;
	public String text;
	public String password;
	public int date;
	public boolean done;


}
