package com.cupk.snapshot.domain.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Guo Tianyou on 2023/6/17.
 */
public class ReportVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private UserVo userVo;
    private Date wfTime;
    private String wfAddress;
    private String wfType;
    private String wfDesc;
    private String hphm;
    private String hpzl;
    private String pic1Url;
    private String pic2Url;
    private String pic3Url;

    @Override
    public String toString() {
        return "ReportVo{" +
                "userVo=" + userVo +
                ", wfTime=" + wfTime +
                ", wfAddress='" + wfAddress + '\'' +
                ", wfType='" + wfType + '\'' +
                ", wfDesc='" + wfDesc + '\'' +
                ", hphm='" + hphm + '\'' +
                ", hpzl='" + hpzl + '\'' +
                ", pic1Url='" + pic1Url + '\'' +
                ", pic2Url='" + pic2Url + '\'' +
                ", pic3Url='" + pic3Url + '\'' +
                '}';
    }

    public UserVo getUserVo() {
        return userVo;
    }

    public void setUserVo(UserVo userVo) {
        this.userVo = userVo;
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

    public String getHphm() {
        return hphm;
    }

    public void setHphm(String hphm) {
        this.hphm = hphm;
    }

    public String getHpzl() {
        return hpzl;
    }

    public void setHpzl(String hpzl) {
        this.hpzl = hpzl;
    }

    public String getPic1Url() {
        return pic1Url;
    }

    public void setPic1Url(String pic1Url) {
        this.pic1Url = pic1Url;
    }

    public String getPic2Url() {
        return pic2Url;
    }

    public void setPic2Url(String pic2Url) {
        this.pic2Url = pic2Url;
    }

    public String getPic3Url() {
        return pic3Url;
    }

    public void setPic3Url(String pic3Url) {
        this.pic3Url = pic3Url;
    }
}
