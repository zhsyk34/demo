package com.cat.demo.entity;

import com.cat.demo.annotation.Column;
import com.cat.demo.annotation.Id;
import com.cat.demo.annotation.Table;

@Table
public class Student {

	@Id("sid")
	private int id;

	@Column("sname")
	private String name;

	@Column("sscore")
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

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", score=" + score + "]";
	}

}
