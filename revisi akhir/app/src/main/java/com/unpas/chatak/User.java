package com.unpas.chatak;

import android.app.Application;
import android.net.Uri;

public class User extends Application {
	private String id;
	private String full_name;
	private String email;
	private String no_hp;
	private String wishlist;
	private String[] order;
	private Uri photo;
	private UserAddressModel userAddressModel;

	public User(String id, String full_name,String email, String no_hp){
		this.id=id;
		this.full_name=full_name;
		this.email=email;
		this.no_hp=no_hp;
	}

	public User(){
		this.id=null;
		this.full_name=null;
		this.email=null;
		this.no_hp=null;
		this.wishlist=null;
	}
	public String getWishlist() {
		return wishlist;
	}

	public void setWishlist(String wishlist) {
		this.wishlist = wishlist;
	}

	public UserAddressModel getUserAddressModel() {
		return userAddressModel;
	}

	public void setUserAddressModel(UserAddressModel userAddressModel) {
		this.userAddressModel = userAddressModel;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getwishlist() {
		return wishlist;
	}

	public void setwishlist(String wishlist) {
		this.wishlist = wishlist;
	}

	public String[] getOrder() {
		return order;
	}

	public void setOrder(String[] order) {
		this.order = order;
	}

	public Uri getPhoto() {
		return photo;
	}

	public void setPhoto(Uri photo) {
		this.photo = photo;
	}

	public String getNo_hp() {
		return no_hp;
	}

	public void setNo_hp(String no_hp) {
		this.no_hp = no_hp;
	}

	public void clearData(){
		setFull_name(null);
		setEmail(null);
		setNo_hp(null);
	}

	public void updateProfile(String id, String email, String full_name, String no_hp){
		 this.id=id;
		 this.full_name=full_name;
		 this.email=email;
		 this.no_hp=no_hp;
	}

	public String getId() {
		return id;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}
}
