package com.fh.entity.param;

import java.math.BigDecimal;

public class DonationBonusParam {

    private String payLogId;

    private BigDecimal orderAmount;

    private Integer userId;

    private String accountSn;

    public String getPayLogId() {
        return payLogId;
    }

    public void setPayLogId(String payLogId) {
        this.payLogId = payLogId;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccountSn() {
        return accountSn;
    }

    public void setAccountSn(String accountSn) {
        this.accountSn = accountSn;
    }
}
