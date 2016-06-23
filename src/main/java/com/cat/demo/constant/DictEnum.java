package com.cat.demo.constant;

public class DictEnum {

	// 住房类型
	public enum GridType {
		HOUSE, ROOM
	}

	// 费用类型
	public enum FeeType {
		WATER, ELECTRIC, GAS, PROPERTY
	}

	// 计价方式
	public enum CalcType {
		AREA, COUNT
	}

	// 订单状态
	public enum OrderStatus {
		RESERVE("预约,用户收藏?"), DEBT("下单未付款"), CHANGE("变更订单"), // (能否修改房源?)
		COMPLETE("已完成"), REFUND("退单"), WAIT("待审核"), PASS("通过退单申请");

		@SuppressWarnings("unused")
		private final String desc;

		OrderStatus(String desc) {
			this.desc = desc;
		}

	}
	// 合同略...可根据订单状态在相应时间节点提供打印需求...退单签字?//TODO
}
