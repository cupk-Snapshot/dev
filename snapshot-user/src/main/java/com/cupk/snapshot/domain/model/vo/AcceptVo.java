package com.cupk.snapshot.domain.model.vo;


import java.io.Serializable;

/**
 * Created by Guo Tianyou on 2023/6/17.
 */
public class AcceptVo implements Serializable {

    private static final long serialVersionUID = 1L;

//    private Long acceptId;
    private ReportVo reportVo;
//    private String level;
//    private Long examineId;
//    private String examineName;
//    private Long handlerId;
//    private String handlerName;
//    private Integer isPublic;
//    // 审核状态：未通过审核、等待审核、通过审核
//    private String status;


    @Override
    public String toString() {
        return "AcceptVo{" +
                "reportVo=" + reportVo +
                '}';
    }


    public ReportVo getReportVo() {
        return reportVo;
    }

    public void setReportVo(ReportVo reportVo) {
        this.reportVo = reportVo;
    }
}
