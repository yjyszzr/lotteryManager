package com.fh.controller.lottery.order;

import java.util.List;

public class ReqOrdeEntity {

	public String orderSn;
	public double reward;
	public int userId;
	public double userMoney;
	public List<ReqOrdeEntity> userIdAndRewardList;
}
