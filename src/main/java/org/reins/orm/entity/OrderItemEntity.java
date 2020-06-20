package org.reins.orm.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name="order_item")
public class OrderItemEntity {
    private String isbn;
    private int id;
    private int amount;
    private int quantity;
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private OrderEntity orderEntity;
    @ManyToOne(targetEntity = OrderEntity.class,fetch = FetchType.EAGER)
    @JoinColumn(name="order_setid",nullable = false)
    public OrderEntity getOrderEntity() {
        return orderEntity;
    }
    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    @Basic
    @Column(name="isbn")
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name="amount")
    public int getAmount(){return amount;}
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItemEntity that = (OrderItemEntity) o;
        if (!Objects.equals(isbn, that.isbn)) return false;
        return true;
    }

    @Basic
    @Column(name="quantity")
    public int getQuantity(){return quantity;}
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
