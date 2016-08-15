package com.ee.enigma.dao;

import com.ee.enigma.model.UserRole;

public interface UserRoleDao {
    public UserRole getUserRole(String userId);

    public void updateUserRole(UserRole userRole);

    public void saveUserRole(UserRole userRole);
}
