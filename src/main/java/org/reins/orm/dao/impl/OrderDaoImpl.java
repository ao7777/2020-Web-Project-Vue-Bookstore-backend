package org.reins.orm.dao.impl;

import org.reins.orm.dao.OrderDao;
import org.reins.orm.entity.OrderEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public OrderEntity getOrderByID(int ID) {
       return entityManager.find(OrderEntity.class,ID);
    }
    @Override
    public void addOrder(OrderEntity orderEntity) {
        entityManager.persist(orderEntity);
    }

    @Override
    public List getOrderByUserID(int ID) {
        return entityManager.createQuery("select o from OrderEntity o where o.user_id=:ID")
                .setParameter("ID",ID).getResultList();
    }

    @Override
    public List getOrders() {
        return entityManager.createQuery("select o from OrderEntity o").getResultList();
    }
}
