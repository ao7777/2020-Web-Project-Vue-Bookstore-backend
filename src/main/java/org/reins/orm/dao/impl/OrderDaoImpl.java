package org.reins.orm.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    public List getOrdersByID(int ID) {
        return entityManager.createQuery("select  o from OrderEntity o where o.id=:ID")
                .setParameter("ID",ID).getResultList();
    }

    @Override
    public void addOrder(List<OrderEntity> list) {
        list.forEach(
                orderEntity -> {
                    entityManager.persist(orderEntity);
                }
        );
    }

    @Override
    public int getNextOrderSetID() {
        Object object= entityManager.createQuery("select max(o.orderSetId) from OrderEntity o").getSingleResult();
        int currentID=0;
        Logger logger= LogManager.getLogger("GetIDWatcher");
        if(object!=null){
            currentID=(int)object;}
        logger.info(currentID);
        return currentID+1;
    }
}
