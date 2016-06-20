package com.cat.demo.entity;

import com.cat.demo.annotation.Column;
import com.cat.demo.annotation.Table;

@Table
public class Student {
	@Column("sid")
	private int id;
	@Column
	private String name;
	@Column
	private double score;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
