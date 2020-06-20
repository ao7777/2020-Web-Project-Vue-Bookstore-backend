package org.reins.orm.entity;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userinfo")
public class UserInfo {
    @Id
    private String _id;
    private int user_id;
    private String profile;
    private int balance;
    private Binary avatar;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
    @Override
    public String toString(){
        return "[id="+ user_id +",description="+ profile +",avatar="+avatar+"]";
    }

    public int getBalance() {
        return balance;
    }

    public Binary getAvatar() {
        return avatar;
    }

    public void setAvatar(Binary avatar) {
        this.avatar = avatar;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
