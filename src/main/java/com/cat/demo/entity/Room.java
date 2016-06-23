package com.cat.demo.entity;

import java.math.BigDecimal;

import com.cat.demo.constant.DictEnum.CalcType;

public class Room {

	private int id;

	private int houseId;

	private String name;// 在house中的索引(名称),同house下不允许重复

	private BigDecimal area;// 面积

	private BigDecimal price;// 单价

	private CalcType calcType;// 计价方式(单价所指...)

	private boolean used;// 是否已使用

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public CalcType getCalcType() {
		return calcType;
	}

	public void setCalcType(CalcType calcType) {
		this.calcType = calcType;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

}
