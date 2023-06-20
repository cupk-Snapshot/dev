package com.cupk.snapshot.domain.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Guo Tianyou on 2023/6/14.
 */
public class OrderVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;
    //商品图片
    private String picUrl;
    //商品描述
    private String title;

    //兑换用户
    private String name;
    //商品积分
    private Integer point;

    private Date createTime;
    //地址信息
//    private AddressVo addressVo;
//    //商品信息
//    private GoodsVo goodsVo;

    @Override
    public String toString() {
        return "OrderVo{" +
                "orderId=" + orderId +
                ", picUrl='" + picUrl + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", point=" + point +
                ", createTime=" + createTime +
//                ", addressVo=" + addressVo +
//                ", goodsVo=" + goodsVo +
                '}';
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

//    public AddressVo getAddressVo() {
//        return addressVo;
//    }
//
//    public void setAddressVo(AddressVo addressVo) {
//        this.addressVo = addressVo;
//    }
//
//    public GoodsVo getGoodsVo() {
//        return goodsVo;
//    }
//
//    public void setGoodsVo(GoodsVo goodsVo) {
//        this.goodsVo = goodsVo;
//    }
}
