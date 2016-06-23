package com.cat.demo.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.cat.demo.constant.DictEnum.CalcType;

//房屋...小区...
public class House {

	private int id;

	private String name;// 名称(坐标)

	private BigDecimal area;// 面积(+公摊...)

	private int rooms;// 房间数量(真实数量,挂名其下出租的room不得超过次数...)

	private BigDecimal price;// 出租单价(未必等于其下所有room的价格之和)

	private CalcType calcType;// 计价方式(单价所指...)

	private Date build;// 模拟小区建筑时间...

	private boolean used;// 是否已使用(其下所有房间的状态...)

	private BigDecimal water;// 当前水刻度

	private BigDecimal electric;// 当前电刻度

	private BigDecimal gas;// 当前煤气刻度

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

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public int getRooms() {
		return rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
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

	public Date getBuild() {
		return build;
	}

	public void setBuild(Date build) {
		this.build = build;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public BigDecimal getWater() {
		return water;
	}

	public void setWater(BigDecimal water) {
		this.water = water;
	}

	public BigDecimal getElectric() {
		return electric;
	}

	public void setElectric(BigDecimal electric) {
		this.electric = electric;
	}

	public BigDecimal getGas() {
		return gas;
	}

	public void setGas(BigDecimal gas) {
		this.gas = gas;
	}

}
