package com.cupk.snapshot.domain.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Guo Tianyou on 2023/6/14.
 */
public class OrderVo  implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;
    //商品图片
    private String picUrl;
    //商品描述
    private String title;

    private String nickName;
    private Integer point;

    private Date createTime;
    private AddressVo addressVo;

    @Override
    public String toString() {
        return "OrderVo{" +
                "orderId=" + orderId +
                ", picUrl='" + picUrl + '\'' +
                ", title='" + title + '\'' +
                ", point=" + point +
                ", nickName='" + nickName + '\'' +
                ", createTime=" + createTime +
                ", addressVo=" + addressVo +
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public AddressVo getAddressVo() {
        return addressVo;
    }

    public void setAddressVo(AddressVo addressVo) {
        this.addressVo = addressVo;
    }
}
