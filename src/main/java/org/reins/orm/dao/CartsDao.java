package org.reins.orm.dao;

import org.reins.orm.entity.CartsEntity;

import java.util.List;

public interface CartsDao {
    List<CartsEntity> getCartByID(int ID);
    void updateCart(List<CartsEntity> list);
    CartsEntity getCartByOrderID(int OrderID);
    void addCart(List<CartsEntity> list);
    void deleteCartByID(int ID);
}
