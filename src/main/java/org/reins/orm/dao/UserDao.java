package org.reins.orm.dao;

import org.reins.orm.entity.UserEntity;
import org.reins.orm.entity.UserInfo;

import java.util.List;

public interface UserDao {
    Object checkUser(String username, String password);
    UserInfo getUserInfo(UserEntity user);
    UserEntity getUserByName(String username);
    UserEntity getUserByID(int ID);
    List<UserEntity> getUsers();
    void AddUser(UserEntity user);
    void updateUser(UserEntity user);
}
