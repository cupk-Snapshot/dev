package com.cupk.snapshot.domain.model.vo;


import java.io.Serializable;

/**
 * Created by Guo Tianyou on 2023/6/17.
 */
public class AcceptVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long acceptId;
    private ReportVo reportVo;
    private String level;
    private Long examineId;
    private String examineName;
    private Long handlerId;
    private String handlerName;
    private Integer isPublic;
    // 审核状态：未通过审核、等待审核、通过审核
    private String status;

    @Override
    public String toString() {
        return "AcceptVo{" +
                "acceptId=" + acceptId +
                ", reportVo=" + reportVo +
                ", level='" + level + '\'' +
                ", examineId=" + examineId +
                ", examineName='" + examineName + '\'' +
                ", handlerId=" + handlerId +
                ", handlerName='" + handlerName + '\'' +
                ", isPublic=" + isPublic +
                ", status='" + status + '\'' +
                '}';
    }

    public Long getAcceptId() {
        return acceptId;
    }

    public void setAcceptId(Long acceptId) {
        this.acceptId = acceptId;
    }

    public ReportVo getReportVo() {
        return reportVo;
    }

    public void setReportVo(ReportVo reportVo) {
        this.reportVo = reportVo;
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
}
