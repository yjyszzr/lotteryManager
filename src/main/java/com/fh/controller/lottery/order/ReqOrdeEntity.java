package com.fh.controller.lottery.order;

import java.util.List;

public class ReqOrdeEntity {

	public String orderSn;
	public double reward;
	public int userId;
	public double userMoney;
	public double betMoney;
	public String betTime;
	public String note;
	public Integer lotteryClassifyId;
	public String lotteryName;
	public List<ReqOrdeEntity> userIdAndRewardList;
}
