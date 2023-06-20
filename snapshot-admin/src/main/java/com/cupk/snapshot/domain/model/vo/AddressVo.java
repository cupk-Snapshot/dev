package com.cupk.snapshot.domain.model.vo;

import java.io.Serializable;

/**
 * Created by Guo Tianyou on 2023/6/14.
 */
public class AddressVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String phoneNum;
    private String area;
    private String address;

    @Override
    public String toString() {
        return "AddressVo{" +
                ", name='" + name + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                '}';
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
