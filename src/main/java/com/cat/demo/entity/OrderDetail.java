package com.cat.demo.entity;

//订单中的房间明细1:n(如果为整租则罗列...)
public class OrderDetail {

	private int id;

	private String orderNo;// 此处参照订单编号(不受订单状态改变的影响)

	private int roomId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

}
