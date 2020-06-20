package org.reins.orm.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reins.orm.dao.UserDao;
import org.reins.orm.entity.AccountInfoEntity;
import org.reins.orm.entity.UserEntity;
import org.reins.orm.entity.UserInfo;
import org.reins.orm.repository.UserInfoRepository;
import org.reins.orm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
    UserRepository userRepository;
    UserInfoRepository userInfoRepository;
    @Autowired
    public UserDaoImpl(UserRepository userRepository,UserInfoRepository userInfoRepository){
        this.userRepository=userRepository;
        this.userInfoRepository=userInfoRepository;
    }
    @Override
    public Object checkUser(String username, String password) {
        return userRepository.checkLogin(username,password);
    }
    @Override
    public UserInfo getUserInfo(UserEntity user) {
        return userInfoRepository.findByUser_id(user.getId()).orElse(null);
    }

    @Override
    public UserEntity getUserByName(String username) {
        return userRepository.findByName(username);
    }

    @Override
    public UserEntity getUserByID(int ID) {
        Optional<UserEntity> user=userRepository.findById(ID);
        return user.orElse(null);
    }

    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void AddUser(UserEntity user) {
        userRepository.saveAndFlush(user);
    }
    @Override
    @Transactional
    public void updateUser(UserEntity user){
        UserEntity userEntity=userRepository.findById(user.getId()).orElse(null);
        if(userEntity!=null){
        userEntity.setPwd(user.getPwd());
        userEntity.setName(user.getName());
        userEntity.setJoinDate(user.getJoinDate());
        userEntity.setAvatar(user.getAvatar());
        userEntity.setType(user.getType());
        userEntity.setCartsEntitySet(user.getCartsEntitySet());
        userEntity.setEmail(user.getEmail());
        userRepository.save(userEntity);
        userRepository.flush();
        }
    }
}
