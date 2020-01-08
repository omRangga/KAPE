package com.unpas.chatak.rest;

import com.unpas.chatak.model.WishlistModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WishlistApiInterface {
    /*Wishlist Interface*/
    //get
    @Headers("content-type: application/json")
    @GET("/wishlist?chatak-api-key=admin123&user_email=fahendil@gmail.com")
    Call<List<WishlistModel>> getWishlist();
//    @GET("/wishlist/")
    //get user wishlist by email
//    Call<List<WishlistModel>> getUser_wishlist(@Query("chatak-api-key") String api_key,
//                                               @Query("user_email") String user_email
//    );
    //post wishlist
    @FormUrlEncoded
    @POST("/wishlist/")
    Call<ResponseBody> postNew_User(
            @Header("chatak-api-key") String api_key,
            @Field("user_email") String new_email,
            @Field("id_services") String new_password
    );
    //delete user wishlist with params email and id_service
    @FormUrlEncoded
    @DELETE("/wishlist/")
    Call<ResponseBody> deleteUser_wishlist(
            @Header("chatak-api-key") String api_key,
            @Field("user_email") String new_email,
            @Field("id_services") String new_password
    );
    /*End of Wishlist Interface*/
}
