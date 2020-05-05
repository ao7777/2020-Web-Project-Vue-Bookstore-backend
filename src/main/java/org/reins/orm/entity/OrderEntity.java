package org.reins.orm.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name="orders")
public class OrderEntity {
    private int id;
    private String name;
    private String isbn;
    private Date transactionDate;
    private int orderId;
    private int amount;
    private int quantity;
    private int orderSetId;
    @Basic
    @Column(name = "id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "isbn")
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "transaction_date")
    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "orderID")
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name="order_setid")
    public int getOrderSetId(){return orderSetId;}
    public void setOrderSetId(int orderSetId) {
        this.orderSetId = orderSetId;
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

        OrderEntity that = (OrderEntity) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(isbn, that.isbn)) return false;
        if (!Objects.equals(transactionDate, that.transactionDate))
            return false;
        if (!Objects.equals(orderId, that.orderId)) return false;

        return true;
    }

    @Basic
    @Column(name="quantity")
    public int getQuantity(){return quantity;}
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
