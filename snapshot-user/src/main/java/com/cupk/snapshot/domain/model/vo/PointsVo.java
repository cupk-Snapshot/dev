package com.cupk.snapshot.domain.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Guo Tianyou on 2023/6/14.
 */
public class PointsVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long pointsId;
    private String description;
    private Integer exchange;
    private Integer balance;
    private Date createTime;

    @Override
    public String toString() {
        return "PointsVo{" +
                "pointsId=" + pointsId +
                ", description='" + description + '\'' +
                ", exchange=" + exchange +
                ", balance=" + balance +
                ", createTime=" + createTime +
                '}';
    }

    public Long getPointsId() {
        return pointsId;
    }

    public void setPointsId(Long pointsId) {
        this.pointsId = pointsId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public Integer getExchange() {
        return exchange;
    }

    public void setExchange(Integer exchange) {
        this.exchange = exchange;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
