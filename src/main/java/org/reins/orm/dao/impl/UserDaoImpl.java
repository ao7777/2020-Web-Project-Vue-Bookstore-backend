package org.reins.orm.dao.impl;

import org.reins.orm.dao.UserDao;
import org.reins.orm.entity.AccountInfoEntity;
import org.reins.orm.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Object checkUser(String username, String password) {
        return entityManager.createQuery("select u from UserEntity u where u.name=:username and u.pwd=:password")
                .setParameter("username",username).setParameter("password",password).getResultList().get(0);
    }
    @Override
    public AccountInfoEntity getUserInfo(UserEntity user) {
        return entityManager.find(AccountInfoEntity.class,user.getId());
    }

    @Override
    public Object getUserByName(String username) {
        return entityManager.createQuery("select u from UserEntity u where u.name=:username")
                .setParameter("username",username).getResultList().get(0);
    }

    @Override
    @Transactional
    public void AddUser(UserEntity user) {
        entityManager.persist(user);
    }
}
