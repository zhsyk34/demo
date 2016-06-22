package com.cat.demo.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.cat.demo.annotation.Column;
import com.cat.demo.annotation.Id;
import com.cat.demo.annotation.Table;
import com.cat.demo.util.Tools;

@Table("student")
public class Stu {

	@Id
	private int id;

	@Column("name")
	private String sname;

	@Column("score")
	private double sscore;

	@Column
	private BigDecimal money;

	@Column
	private Date birthday;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public double getSscore() {
		return sscore;
	}

	public void setSscore(double sscore) {
		this.sscore = sscore;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", sname=" + sname + ", sscore=" + sscore + ", money=" + money + ", birthday=" + Tools.printDate(birthday) + "]";
	}

}
