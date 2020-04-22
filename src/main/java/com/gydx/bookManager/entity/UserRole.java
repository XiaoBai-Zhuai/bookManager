package com.gydx.bookManager.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "user_role")
public class UserRole {

    @Column
    private Integer userId;
    @Column
    private Integer roleId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
