package com.cat.demo.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Company {

	private int id;

	private String name;

	private String address;

	private Date createtime;

	private double money;

	private float total;

	private boolean flag;

	private BigDecimal all;

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public BigDecimal getAll() {
		return all;
	}

	public void setAll(BigDecimal all) {
		this.all = all;
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + ", address=" + address + ", createtime=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createtime) + ", money=" + money + ", total=" + total + ", flag=" + flag + ", all=" + all + "]";
	}

	// new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createtime)

}
