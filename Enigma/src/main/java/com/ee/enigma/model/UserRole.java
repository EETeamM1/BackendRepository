package com.ee.enigma.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ROLES")
public class UserRole {

    @Id
    @Column(name = "user_Role_Id")
    private int userRoleId;
    private String userId;
    private String role;

    /* GETTERS AND SETTERS */
    public int getRoleId() {
        return userRoleId;
    }

    public void setRoleId(int userId) {
        this.userRoleId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "userId: " + this.getUserId() + ", userName: " + this.getUserId() + ", password: " + this.getRole();
    }
}
