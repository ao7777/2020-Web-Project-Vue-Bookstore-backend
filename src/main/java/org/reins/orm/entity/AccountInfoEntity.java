package org.reins.orm.entity;

import javax.persistence.*;

@Entity
@Table(name = "accountinfo", schema = "bookstoredata", catalog = "")
public class AccountInfoEntity {
    private String id;
    private int balance;
    private String profile;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "balance")
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "profile")
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountInfoEntity that = (AccountInfoEntity) o;

        if (balance != that.balance) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (profile != null ? !profile.equals(that.profile) : that.profile != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + balance;
        result = 31 * result + (profile != null ? profile.hashCode() : 0);
        return result;
    }
}
