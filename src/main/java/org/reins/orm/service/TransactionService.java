package org.reins.orm.service;

import org.reins.orm.dao.BookDao;
import org.reins.orm.dao.CartsDao;
import org.reins.orm.dao.OrderDao;
import org.reins.orm.entity.BookEntity;
import org.reins.orm.entity.CartsEntity;
import org.reins.orm.entity.OrderEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.print.attribute.standard.DateTimeAtProcessing;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class TransactionService {
    @Resource
    private OrderDao orderDao;
    @Resource
    private CartsDao cartsDao;
    @Resource
    private BookDao bookDao;
    @Transactional
    public void submitOrder(List<CartsEntity> list){
        List<OrderEntity> orderEntityList = new LinkedList<>();
        Date d1=new Date();
        java.sql.Date date=new java.sql.Date(d1.getTime());
        int orderSetID=orderDao.getNextOrderSetID();
        list.forEach(
                cartsEntity -> {
                    OrderEntity orderEntity = new OrderEntity();
                    orderEntity.setAmount(bookDao.getBookByISBN(cartsEntity.getIsbn()).getPrice());
                    orderEntity.setId(cartsEntity.getId());
                    orderEntity.setIsbn(cartsEntity.getIsbn());
                    orderEntity.setName(cartsEntity.getName());
                    orderEntity.setOrderSetId(orderSetID);
                    orderEntity.setTransactionDate(date);
                    orderEntity.setQuantity(cartsEntity.getQuantity());
                    orderEntityList.add(orderEntity);
                    BookEntity bookEntity=bookDao.getBookByISBN(orderEntity.getIsbn());
                    bookEntity.setStorage(bookEntity.getStorage()-cartsEntity.getQuantity());
                    bookDao.updateBook(bookEntity);
                }
        );
        orderDao.addOrder(orderEntityList);
        cartsDao.deleteCartByID(list.get(0).getId());
    }
    @Transactional
    public void updateCart(List<CartsEntity> list){
        cartsDao.updateCart(list);
    }
    public List<OrderEntity> getOrderByID(int ID){
        return orderDao.getOrdersByID(ID);
    }
    public List<CartsEntity> getCartByID(int ID){
        return cartsDao.getCartByID(ID);
    }
}
