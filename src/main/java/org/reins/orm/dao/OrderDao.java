package org.reins.orm.dao;

import org.reins.orm.entity.OrderEntity;

import java.util.List;

public interface OrderDao {
    List<OrderEntity> getOrdersByID(int ID);
    void addOrder(List<OrderEntity> list);
    int getNextOrderSetID();
}
