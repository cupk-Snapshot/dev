package com.cupk.snapshot.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName wfssp_points
 */
@TableName(value ="wfssp_points")
public class Points implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 积分表主键
     */
    @TableId(value = "points_id")
    private Long pointsId;

    /**
     * 用户表主键
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 积分描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 积分变化
     */
    @TableField(value = "exchange")
    private Integer exchange;

    /**
     * 积分余额
     */
    @TableField(value = "balance")
    private Integer balance;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 删除标识符
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    public Points() {
    }

    public Points(Long userId, String description, Integer exchange, Integer balance) {
        this.userId = userId;
        this.description = description;
        this.exchange = exchange;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Points{" +
                "pointsId=" + pointsId +
                ", userId=" + userId +
                ", description='" + description + '\'' +
                ", exchange=" + exchange +
                ", balance=" + balance +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDeleted=" + isDeleted +
                '}';
    }

    public Long getPointsId() {
        return pointsId;
    }

    public void setPointsId(Long pointsId) {
        this.pointsId = pointsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}