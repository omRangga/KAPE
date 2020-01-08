package com.unpas.chatak.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishlistModel {

    @SerializedName("title")
    @Expose
    private String wishlistTitle;

    @SerializedName("description")
    @Expose
    private String wishlistDescription;

    @SerializedName("image")
    @Expose
    private String wishlistImage;

    public WishlistModel() {
    }

    public WishlistModel(String wishlistTitle, String wishlistDescription, String wishlistImage) {
        this.wishlistTitle = wishlistTitle;
        this.wishlistDescription = wishlistDescription;
        this.wishlistImage = wishlistImage;
    }

    public String getWishlistTitle() {
        return wishlistTitle;
    }

    public void setWishlistTitle(String wishlistTitle) {
        this.wishlistTitle = wishlistTitle;
    }

    public String getWishlistDescription() {
        return wishlistDescription;
    }

    public void setWishlistDescription(String wishlistDescription) {
        this.wishlistDescription = wishlistDescription;
    }

    public String getWishlistImage() {
        return wishlistImage;
    }

    public void setWishlistImage(String wishlistImage) {
        this.wishlistImage = wishlistImage;
    }
}
