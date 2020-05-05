package org.reins.orm.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reins.orm.dao.CartsDao;
import org.reins.orm.entity.CartsEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CartsDaoImpl implements CartsDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List getCartByID(int ID) {
        return entityManager.createQuery("select c from CartsEntity  c where  c.id=:ID")
                .setParameter("ID",ID).getResultList();
    }

    @Override
    public void updateCart(List<CartsEntity> list) {
        deleteCartByID(list.get(0).getId());
        addCart(list);
    }
    @Override
    public CartsEntity getCartByOrderID(int OrderID) {
        return entityManager.find(CartsEntity.class,OrderID);
    }

    @Override
    public void addCart(List<CartsEntity> list) {
        list.forEach(
                cartsEntity -> {
                    entityManager.persist(cartsEntity);
                }
        );
    }

    @Override
    @Transactional
    public void deleteCartByID(int ID) {
        Logger logger= LogManager.getLogger("DeleteWatcher");
        logger.info(ID);
        entityManager.createQuery("delete from CartsEntity c where c.id=:ID")
        .setParameter("ID",ID).executeUpdate();
    }
}
