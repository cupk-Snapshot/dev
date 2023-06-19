package com.cupk.snapshot.domain.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Guo Tianyou on 2023/6/17.
 */
public class ReportVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private UserVo userVo;
//    private Date wfTime;
//    private String wfAddress;
//    private String wfType;
//    private String wfDesc;
//    private String hphm;
//    private String hpzl;
//    private String pic1Url;
//    private String pic2Url;
//    private String pic3Url;

    private List<Map<String, String>> urls;

    @Override
    public String toString() {
        return "ReportVo{" +
                "userVo=" + userVo +
                ", urls=" + urls +
                '}';
    }

    public UserVo getUserVo() {
        return userVo;
    }

    public void setUserVo(UserVo userVo) {
        this.userVo = userVo;
    }

    public List<Map<String, String>> getUrls() {
        return urls;
    }

    public void setUrls(List<Map<String, String>> urls) {
        this.urls = urls;
    }
}
