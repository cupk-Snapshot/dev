package com.cupk.snapshot.domain.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Guo Tianyou on 2023/6/14.
 */
public class OrderVo  implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;
    private String picUrl;
    private String title;
    private Integer point;
    private String name;
    private Date createTime;
    private AddressVo addressVo;

    @Override
    public String toString() {
        return "OrderVo{" +
                "orderId=" + orderId +
                ", picUrl='" + picUrl + '\'' +
                ", title='" + title + '\'' +
                ", point=" + point +
                ", name='" + name + '\'' +
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

    public AddressVo getAddressVo() {
        return addressVo;
    }

    public void setAddressVo(AddressVo addressVo) {
        this.addressVo = addressVo;
    }
}
