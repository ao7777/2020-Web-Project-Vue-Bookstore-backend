package org.reins.orm.service;

import org.reins.orm.dao.UserDao;
import org.reins.orm.entity.AccountInfoEntity;
import org.reins.orm.entity.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserDao userDao;
    public UserEntity checkLogin(String username, String password){
        return (UserEntity) userDao.checkUser(username,password);
    }
    public AccountInfoEntity getAccountInfo(String username){
        UserEntity userEntity=(UserEntity) userDao.getUserByName(username);
        return userDao.getUserInfo(userEntity);
    }
    public void AddUser(UserEntity userEntity){
        userDao.AddUser(userEntity);
    }
}
