package org.reins.orm.service.impl;

import org.reins.orm.dao.BookDao;
import org.reins.orm.dao.CartsDao;
import org.reins.orm.dao.OrderDao;
import org.reins.orm.dao.UserDao;
import org.reins.orm.entity.*;
import org.reins.orm.service.TransactionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private BookDao bookDao;
    @Resource
    private UserDao userDao;
    @Resource
    private CartsDao cartsDao;
    @Transactional
    public void submitOrder(UserEntity user){
        List<OrderItemEntity> orderEntityList = new LinkedList<>();
        OrderEntity orderEntity = new OrderEntity();
        java.util.Date time=new Date();
        java.sql.Timestamp date=new java.sql.Timestamp(time.getTime());
        orderEntity.setTransaction_date(date);
        orderEntity.setUser_id(user.getId());
        user.getCartsEntitySet().forEach(
                cartsEntity -> {
                    OrderItemEntity orderItemEntity = new OrderItemEntity();
                    orderItemEntity.setAmount(bookDao.getBookByISBN(cartsEntity.getIsbn()).getPrice());
                    orderItemEntity.setIsbn(cartsEntity.getIsbn());
                    orderItemEntity.setQuantity(cartsEntity.getQuantity());
                    orderItemEntity.setOrderEntity(orderEntity);
                    orderEntityList.add(orderItemEntity);
                    BookEntity bookEntity = bookDao.getBookByISBN(orderItemEntity.getIsbn());
                    bookEntity.setStorage(bookEntity.getStorage() - cartsEntity.getQuantity());
                    bookEntity.setSales(bookEntity.getSales() + cartsEntity.getQuantity());
                    bookDao.updateBook(bookEntity);
                }
        );
        orderEntity.setOrderItem(orderEntityList);
        orderDao.addOrder(orderEntity);
        user.getCartsEntitySet().clear();
    }
    @Transactional
    public void updateCartByID(Set<CartsEntity> list, int ID){
        UserEntity user=userDao.getUserByID(ID);
        list.forEach(
                cartsEntity -> {
                    cartsEntity.setUserEntity(user);
                }
        );
        cartsDao.deleteCartByID(ID);
        user.setCartsEntitySet(list);
        userDao.updateUser(user);
    }
    public List<OrderItemEntity> getOrderByID(int ID){
        OrderEntity orderEntity=orderDao.getOrderByID(ID);
        return orderEntity.getOrderItem();
    }
    @Transactional
    public List<OrderEntity> getOrders(){
        return orderDao.getOrders();
    }
    public Set<CartsEntity> getCartByID(int ID){
        UserEntity userEntity=userDao.getUserByID(ID);
        return userEntity.getCartsEntitySet();
    }
    public List<OrderEntity> getOrderByUserID(int ID){
        return orderDao.getOrderByUserID(ID);
    }
}
