package org.reins.orm.dao;

import org.reins.orm.entity.OrderEntity;

import java.util.List;

public interface OrderDao {
    OrderEntity getOrderByID(int ID);
    void addOrder(OrderEntity orderEntity);
    List getOrderByUserID(int ID);
    List getOrders();
}
