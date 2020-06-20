package org.reins.orm.dao;

import org.reins.orm.entity.CartsEntity;

import java.util.List;

public interface CartsDao {
    void addCart(List<CartsEntity> list);
    void deleteCartByID(int ID);
}
