package com.cupk.snapshot.domain.model.vo;

import java.io.Serializable;

/**
 * Created by Guo Tianyou on 2023/6/14.
 */
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String nickName;
    private String avatarUrl;
    private String username;
    private String phoneNum;
    private String name;

    public UserVo() {
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", username='" + username + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", name='" + name + '\'' +
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


    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
