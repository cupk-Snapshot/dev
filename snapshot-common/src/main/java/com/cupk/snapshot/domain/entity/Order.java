package com.cupk.snapshot.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName wfssp_order
 */
@TableName(value ="wfssp_order")
public class Order implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @TableId(value = "order_id")
    private Long orderId;

    /**
     * 兑换商品主键
     */
    @TableField(value = "goods_id")
    private Long goodsId;

    /**
     * 兑换人主键
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 兑换人姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 兑换人手机号
     */
    @TableField(value = "phone_num")
    private String phoneNum;

    /**
     * 收货地址主键
     */
    @TableField(value = "address_id")
    private Long addressId;

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

    public Order() {
    }

    public Order(Long goodsId, Long userId, String name, String phoneNum, Long addressId) {
        this.goodsId = goodsId;
        this.userId = userId;
        this.name = name;
        this.phoneNum = phoneNum;
        this.addressId = addressId;
    }

    public Order(Long orderId, Long goodsId, Long userId, String name, String phoneNum, Long addressId) {
        this.orderId = orderId;
        this.goodsId = goodsId;
        this.userId = userId;
        this.name = name;
        this.phoneNum = phoneNum;
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", goodsId=" + goodsId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", addressId=" + addressId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDeleted=" + isDeleted +
                '}';
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
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