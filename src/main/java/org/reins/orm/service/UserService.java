package org.reins.orm.service;

import org.reins.orm.entity.AccountInfoEntity;
import org.reins.orm.entity.UserEntity;
import org.reins.orm.entity.UserInfo;

import java.util.List;

public interface UserService {
    UserEntity checkLogin(String username, String password);
    UserInfo getUserInfo(String username);
    void AddUser(UserEntity userEntity);
    UserEntity getUserByID(int ID);
    UserEntity getUserByName(String username);
    List<UserEntity> getUsers();
    void updateUser(UserEntity userEntity);
}
