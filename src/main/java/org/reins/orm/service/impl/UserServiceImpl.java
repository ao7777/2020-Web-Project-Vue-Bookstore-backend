package org.reins.orm.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reins.orm.dao.UserDao;
import org.reins.orm.entity.AccountInfoEntity;
import org.reins.orm.entity.UserEntity;
import org.reins.orm.entity.UserInfo;
import org.reins.orm.service.UserService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    public UserEntity checkLogin(String username, String password){
        return (UserEntity) userDao.checkUser(username,password);
    }
    public UserInfo getUserInfo(String username){
        UserEntity userEntity= userDao.getUserByName(username);
        return userDao.getUserInfo(userEntity);
    }
    public void AddUser(UserEntity userEntity){

        java.util.Date d1=new java.util.Date();
        java.sql.Date date=new java.sql.Date(d1.getTime());
        userEntity.setJoinDate(date);
        userDao.AddUser(userEntity);
    }

    @Override
    public UserEntity getUserByID(int ID) {
        return userDao.getUserByID(ID);
    }

    @Override
    public UserEntity getUserByName(String username) {
        return userDao.getUserByName(username);
    }

    @Override
    public List<UserEntity> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public void updateUser(UserEntity userEntity) {
        userDao.updateUser(userEntity);
    }

}
