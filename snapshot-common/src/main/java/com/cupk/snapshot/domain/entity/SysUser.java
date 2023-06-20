package com.cupk.snapshot.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName sys_user
 */
@TableName(value ="sys_user")
public class SysUser implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 用户表主键
     */
    @TableId(value = "user_id")
    private Long userId;

    /**
     * 用户昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 用户头像地址
     */
    @TableField(value = "avatar_url")
    private String avatarUrl;

    /**
     * 登录用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 登录密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 用户姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 身份证号码
     */
    @TableField(value = "idcard")
    private String idcard;

    /**
     * 手机号
     */
    @TableField(value = "phone_num")
    private String phoneNum;

    /**
     * 账号是否过期
     */
    @TableField(value = "is_accountNon_expired")
    private Integer isAccountnonExpired;

    /**
     * 账号锁定或未锁定
     */
    @TableField(value = "is_accountNon_locked")
    private Integer isAccountnonLocked;

    /**
     * 账号密码是否过期
     */
    @TableField(value = "is_credentialsNon_expired")
    private Integer isCredentialsnonExpired;

    /**
     * 账号启用或禁用
     */
    @TableField(value = "is_enabled")
    private Integer isEnabled;

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

    public SysUser() {
    }

    public SysUser(String username, String password, String phoneNum) {
        this.username = username;
        this.password = password;
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", idcard='" + idcard + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", isAccountnonExpired=" + isAccountnonExpired +
                ", isAccountnonLocked=" + isAccountnonLocked +
                ", isCredentialsnonExpired=" + isCredentialsnonExpired +
                ", isEnabled=" + isEnabled +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDeleted=" + isDeleted +
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Integer getIsAccountnonExpired() {
        return isAccountnonExpired;
    }

    public void setIsAccountnonExpired(Integer isAccountnonExpired) {
        this.isAccountnonExpired = isAccountnonExpired;
    }

    public Integer getIsAccountnonLocked() {
        return isAccountnonLocked;
    }

    public void setIsAccountnonLocked(Integer isAccountnonLocked) {
        this.isAccountnonLocked = isAccountnonLocked;
    }

    public Integer getIsCredentialsnonExpired() {
        return isCredentialsnonExpired;
    }

    public void setIsCredentialsnonExpired(Integer isCredentialsnonExpired) {
        this.isCredentialsnonExpired = isCredentialsnonExpired;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
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