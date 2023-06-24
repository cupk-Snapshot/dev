package com.cupk.snapshot.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName wfssp_accept
 */
@TableName(value ="wfssp_accept")
public class Accept implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 受理表主键
     */
    @TableId(value = "accept_id")
    private Long acceptId;

    /**
     * 举报表主键
     */
    @TableField(value = "report_id")
    private Long reportId;

    /**
     * 违法级别
     */
    @TableField(value = "level")
    private String level;

    /**
     * 审核人员主键
     */
    @TableField(value = "examine_id")
    private Long examineId;

    /**
     * 审核人员姓名
     */
    @TableField(value = "examine_name")
    private String examineName;

    /**
     * 处理人员主键
     */
    @TableField(value = "handler_id")
    private Long handlerId;

    /**
     * 处理人员姓名
     */
    @TableField(value = "handler_name")
    private String handlerName;

    /**
     * 是否公开
     */
    @TableField(value = "is_public")
    private Integer isPublic;

    /**
     * 审核状态（未通过审核，等待审核，通过审核）
     */
    @TableField(value = "status")
    private String status;

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

    public Accept(Long reportId, Integer isPublic, String status) {
        this.reportId = reportId;
        this.isPublic = isPublic;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Accept{" +
                "acceptId=" + acceptId +
                ", reportId=" + reportId +
                ", level='" + level + '\'' +
                ", examineId=" + examineId +
                ", examineName='" + examineName + '\'' +
                ", handlerId=" + handlerId +
                ", handlerName='" + handlerName + '\'' +
                ", isPublic=" + isPublic +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDeleted=" + isDeleted +
                '}';
    }

    public Long getAcceptId() {
        return acceptId;
    }

    public void setAcceptId(Long acceptId) {
        this.acceptId = acceptId;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Long getExamineId() {
        return examineId;
    }

    public void setExamineId(Long examineId) {
        this.examineId = examineId;
    }

    public String getExamineName() {
        return examineName;
    }

    public void setExamineName(String examineName) {
        this.examineName = examineName;
    }

    public Long getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(Long handlerId) {
        this.handlerId = handlerId;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public Integer getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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