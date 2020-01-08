package com.unpas.chatak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("user_id")
    @Expose
    private int user_id;
    @SerializedName("user_email")
    @Expose
    private String user_email;
    @SerializedName("user_password")
    @Expose
    private String user_password;
    @SerializedName("user_full_name")
    @Expose
    private String user_full_name;
    @SerializedName("user_phone_number")
    @Expose
    private String user_phone_number;

    @SerializedName("user_wishlist")
    @Expose
    private Long user_wishlist;

    @SerializedName("status")
    @Expose
    private String status;


    public UserModel(int user_id, String user_email, String user_password, String user_full_name,
					 String user_phone_number, Long user_wishlist){
        this.user_id=user_id;
        this.user_email=user_email;
        this.user_password=user_password;
        this.user_full_name=user_full_name;
        this.user_phone_number=user_phone_number;
        this.user_wishlist=user_wishlist;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_full_name() {
        return user_full_name;
    }

    public void setUser_full_name(String user_full_name) {
        this.user_full_name = user_full_name;
    }

    public String getUser_phone_number() {
        return user_phone_number;
    }

    public void setUser_phone_number(String user_phone_number) {
        this.user_phone_number = user_phone_number;
    }

    public Long getUser_wishlist() {
        return user_wishlist;
    }

    public void setUser_wishlist(Long user_wishlist) {
        this.user_wishlist = user_wishlist;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
