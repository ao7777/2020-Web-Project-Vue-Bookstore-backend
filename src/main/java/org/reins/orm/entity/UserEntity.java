package org.reins.orm.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user", schema = "bookstoredata", catalog = "")
public class UserEntity {
    private int id;
    private String name;
    private String pwd;
    private Date joinDate;
    private String type;
    private String avatar;
    private String email;
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

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "pwd")
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Basic
    @Column(name = "join_date")
    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name="avatar")
    public String getAvatar(){return  avatar;}
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Basic
    @Column(name="email")
    public String getEmail(){return email;}

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(pwd, that.pwd)) return false;
        if (!Objects.equals(joinDate, that.joinDate)) return false;
        if (!Objects.equals(type, that.type)) return false;

        return true;
    }

    private Set<CartsEntity> cartsEntitySet;
    @OneToMany(mappedBy = "userEntity",targetEntity = CartsEntity.class,fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    public Set<CartsEntity> getCartsEntitySet() {
        return cartsEntitySet;
    }
    public void setCartsEntitySet(Set<CartsEntity> cartsEntitySet) {
        this.cartsEntitySet = cartsEntitySet;
    }

}