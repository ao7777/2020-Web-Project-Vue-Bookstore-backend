package org.reins.orm.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="orders")
public class OrderEntity {
    private int order_id;
    private int user_id;
    private Timestamp transaction_date;
    private List<OrderItemEntity> orderItem;
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "order_id")
    public int getOrder_id(){
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    @Basic
    @Column(name="user_id")
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Basic
    @Column(name = "transaction_date")
    public Timestamp getTransaction_date() {
        return transaction_date;
    }
    public void setTransaction_date(Timestamp transaction_date) {
        this.transaction_date = transaction_date;
    }

    @OneToMany(mappedBy = "orderEntity",targetEntity = OrderItemEntity.class,fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public List<OrderItemEntity> getOrderItem() {
        return orderItem;
    }
    public void setOrderItem(List<OrderItemEntity> orderItem) {
        this.orderItem = orderItem;
    }
}
