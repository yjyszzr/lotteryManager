package com.fh.entity.param;

import java.math.BigDecimal;

public class RechargePraiseParam {

    private Integer userId;

    private BigDecimal money;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
