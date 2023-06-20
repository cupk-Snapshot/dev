package com.cupk.snapshot.domain.model.vo;

import java.io.Serializable;

public class SysRoleVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String roleName;
    private String roleCode;

    public String getRoleName() {
        return roleName;
    }

    @Override
    public String toString() {
        return "SysRoleVo{" +
                "roleName='" + roleName + '\'' +
                ", roleCode='" + roleCode + '\'' +
                '}';
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
