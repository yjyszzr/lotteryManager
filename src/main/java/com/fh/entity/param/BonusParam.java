package com.fh.entity.param;

import java.io.Serializable;
import java.math.BigDecimal;


public class BonusParam implements Serializable {

    private Integer bonusId;

    private String bonusSn;

    private BigDecimal bonusPrice;

    private Integer receiveTime;

    private Integer startTime;

    private Integer endTime;

    private Integer addTime;

    private Integer bonusStatus;

    private Integer isDelete;
    
    private Integer isRead;
    
    public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	private Integer useRange;
    
    private BigDecimal minGoodsAmount;
    

	public Integer getUseRange() {
		return useRange;
	}

	public void setUseRange(Integer useRange) {
		this.useRange = useRange;
	}

	public BigDecimal getMinGoodsAmount() {
		return minGoodsAmount;
	}

	public void setMinGoodsAmount(BigDecimal minGoodsAmount) {
		this.minGoodsAmount = minGoodsAmount;
	}


    /**
     * 获取红包编号
     *
     * @return bonus_id - 红包编号
     */
    public Integer getBonusId() {
        return bonusId;
    }

    /**
     * 设置红包编号
     *
     * @param bonusId 红包编号
     */
    public void setBonusId(Integer bonusId) {
        this.bonusId = bonusId;
    }

    /**
     * @return bonus_sn
     */
    public String getBonusSn() {
        return bonusSn;
    }

    /**
     * @param bonusSn
     */
    public void setBonusSn(String bonusSn) {
        this.bonusSn = bonusSn;
    }

    /**
     * 获取红包面值
     *
     * @return bonus_price - 红包面值
     */
    public BigDecimal getBonusPrice() {
        return bonusPrice;
    }

    /**
     * 设置红包面值
     *
     * @param bonusPrice 红包面值
     */
    public void setBonusPrice(BigDecimal bonusPrice) {
        this.bonusPrice = bonusPrice;
    }

    /**
     * 获取领取时间
     *
     * @return receive_time - 领取时间
     */
    public Integer getReceiveTime() {
        return receiveTime;
    }

    /**
     * 设置领取时间
     *
     * @param receiveTime 领取时间
     */
    public void setReceiveTime(Integer receiveTime) {
        this.receiveTime = receiveTime;
    }

    /**
     * 获取红包开始使用时间
     *
     * @return start_time - 红包开始使用时间
     */
    public Integer getStartTime() {
        return startTime;
    }

    /**
     * 设置红包开始使用时间
     *
     * @param startTime 红包开始使用时间
     */
    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取红包结束使用时间
     *
     * @return end_time - 红包结束使用时间
     */
    public Integer getEndTime() {
        return endTime;
    }

    /**
     * 设置红包结束使用时间
     *
     * @param endTime 红包结束使用时间
     */
    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取创建时间
     *
     * @return add_time - 创建时间
     */
    public Integer getAddTime() {
        return addTime;
    }

    /**
     * 设置创建时间
     *
     * @param addTime 创建时间
     */
    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    /**
     * 获取红包使用状态 0-未使用 1-已使用 2已过期
     *
     * @return bonus_status - 红包使用状态 0-未使用 1-已使用 2已过期
     */
    public Integer getBonusStatus() {
        return bonusStatus;
    }

    /**
     * 设置红包使用状态 0-未使用 1-已使用 2已过期
     *
     * @param bonusStatus 红包使用状态 0-未使用 1-已使用 2已过期
     */
    public void setBonusStatus(Integer bonusStatus) {
        this.bonusStatus = bonusStatus;
    }

    /**
     * 获取是否可用
     *
     * @return is_delete - 是否可用
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否可用
     *
     * @param isDelete 是否可用
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}