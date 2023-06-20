package com.cupk.snapshot.domain.model.vo;

import java.io.Serializable;
import java.util.Date;

public class MyReport2Vo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date wfTime;
    private String wfAddress;
    private String wfType;
    private String wfDesc;


    @Override
    public String toString() {
        return "MyReportVo{" +
                "wfTime=" + wfTime +
                ", wfAddress='" + wfAddress + '\'' +
                ", wfType='" + wfType + '\'' +
                ", wfDesc='" + wfDesc + '\'' +
                '}';
    }

    public Date getWfTime() {
        return wfTime;
    }

    public void setWfTime(Date wfTime) {
        this.wfTime = wfTime;
    }

    public String getWfAddress() {
        return wfAddress;
    }

    public void setWfAddress(String wfAddress) {
        this.wfAddress = wfAddress;
    }

    public String getWfType() {
        return wfType;
    }

    public void setWfType(String wfType) {
        this.wfType = wfType;
    }

    public String getWfDesc() {
        return wfDesc;
    }

    public void setWfDesc(String wfDesc) {
        this.wfDesc = wfDesc;
    }

}
