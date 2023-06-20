package com.cupk.snapshot.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName wfssp_report
 */
@TableName(value ="wfssp_report")
public class Report implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 举报表主键
     */
    @TableId(value = "report_id")
    private Long reportId;

    /**
     * 用户表主键
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 违法时间
     */
    @TableField(value = "wf_time", fill = FieldFill.INSERT)
    private Date wfTime;

    /**
     * 违法地点
     */
    @TableField(value = "wf_address")
    private String wfAddress;

    /**
     * 违法类型
     */
    @TableField(value = "wf_type")
    private String wfType;

    /**
     * 违法描述
     */
    @TableField(value = "wf_desc")
    private String wfDesc;

    /**
     * 号牌号码
     */
    @TableField(value = "hphm")
    private String hphm;

    /**
     * 号牌种类
     */
    @TableField(value = "hpzl")
    private String hpzl;

    /**
     * 违法图片1地址
     */
    @TableField(value = "pic1_url")
    private String pic1Url;

    /**
     * 违法图片2地址
     */
    @TableField(value = "pic2_url")
    private String pic2Url;

    /**
     * 违法图片3地址
     */
    @TableField(value = "pic3_url")
    private String pic3Url;

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

    public Report() {
    }

    public Report(Long userId, String wfAddress, String wfType, String hphm, String pic1Url, String pic2Url, String pic3Url) {
        this.userId = userId;
        this.wfAddress = wfAddress;
        this.wfType = wfType;
        this.hphm = hphm;
        this.pic1Url = pic1Url;
        this.pic2Url = pic2Url;
        this.pic3Url = pic3Url;
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", userId=" + userId +
                ", wfTime=" + wfTime +
                ", wfAddress='" + wfAddress + '\'' +
                ", wfType='" + wfType + '\'' +
                ", wfDesc='" + wfDesc + '\'' +
                ", hphm='" + hphm + '\'' +
                ", hpzl='" + hpzl + '\'' +
                ", pic1Url='" + pic1Url + '\'' +
                ", pic2Url='" + pic2Url + '\'' +
                ", pic3Url='" + pic3Url + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDeleted=" + isDeleted +
                '}';
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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