package com.cat.demo.entity;

import java.math.BigDecimal;

import com.cat.demo.constant.DictEnum.FeeType;

//计费参照
public class HouseFee {

	private int id;

	private int houseId;

	private FeeType type;// 费用类型

	private BigDecimal unit;// 单价

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

	public FeeType getType() {
		return type;
	}

	public void setType(FeeType type) {
		this.type = type;
	}

	public BigDecimal getUnit() {
		return unit;
	}

	public void setUnit(BigDecimal unit) {
		this.unit = unit;
	}

}
