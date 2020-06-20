package org.reins.orm.service;

import org.reins.orm.entity.CartsEntity;
import org.reins.orm.entity.OrderEntity;
import org.reins.orm.entity.OrderItemEntity;
import org.reins.orm.entity.UserEntity;

import java.util.List;
import java.util.Set;

public interface TransactionService {
    void submitOrder(UserEntity user);
    void updateCartByID(Set<CartsEntity> list, int ID);
    List<OrderItemEntity> getOrderByID(int ID);
    List<OrderEntity> getOrders();
    Set<CartsEntity>  getCartByID(int ID);
    List<OrderEntity> getOrderByUserID(int ID);
}
