package com.cupk.snapshot.domain.model.vo;

import com.cupk.snapshot.domain.entity.SysRole;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SysUserVo  implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String nickName;
    private String avatarUrl;
    private String username;
    private String name;
    private String idcard;
    private SysRoleVo sysRoleVo;
    private String phoneNum;
    private Date createTime;
    private Integer isEnabled;

    @Override
    public String toString() {
        return "SysUserVo{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", idcard='" + idcard + '\'' +
                ", sysRoleVo=" + sysRoleVo +
                ", phoneNum='" + phoneNum + '\'' +
                ", createTime=" + createTime +
                ", isEnabled=" + isEnabled +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public SysRoleVo getSysRoleVo() {
        return sysRoleVo;
    }

    public void setSysRoleVo(SysRoleVo sysRoleVo) {
        this.sysRoleVo = sysRoleVo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
