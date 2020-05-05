package org.reins.orm.dao;

import org.reins.orm.entity.AccountInfoEntity;
import org.reins.orm.entity.UserEntity;

public interface UserDao {
    Object checkUser(String username, String password);
    AccountInfoEntity getUserInfo(UserEntity user);
    Object getUserByName(String username);
    void AddUser(UserEntity user);
}
