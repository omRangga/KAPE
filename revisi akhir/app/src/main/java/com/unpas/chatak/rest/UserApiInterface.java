package com.unpas.chatak.rest;

import com.unpas.chatak.model.PostModel;
import com.unpas.chatak.model.PutModel;
import com.unpas.chatak.model.UserModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;


public interface UserApiInterface {
    /*User Interface*/
    //get
    @Headers("content-type: application/json")
    @GET("/user/")
    Call<List<UserModel>> getUser(@Query("chatak-api-key") String key);
    //get user by email
    @Headers("content-type: application/json")
    @GET("/user/")
    Call<List<UserModel>> getUser_email(@Query("chatak-api-key") String api_key,
                                        @Query("email") String user_email
    );
    //post user
    @FormUrlEncoded
    @POST("/user/")
    Call<ResponseBody> postNew_User(
            @Header("chatak-api-key") String api_key,
            @Field("email") String new_email,
            @Field("password") String new_password,
            @Field("full_name") String new_full_name,
            @Field("phone_number") String new_phone_number
    );
    //put user
    @FormUrlEncoded
    @PUT("/user/")
    Call<PutModel> putCurrentUser(
            @Field("chatak-api-key") String api_key,
            @Field("user_id") String user_id,
            @Field("email") String email,
            @Field("password") String password,
            @Field("full_name") String full_name,
            @Field("phone_number") String phone_number
    );
    //post photo profile
    @Multipart
    @POST("/photo/")
    Call<PostModel> postPhoto_profile(
            @Header("chatak-api-key") String api_key,
            @Part MultipartBody.Part file,
            @Part("photo") RequestBody requestBody
    );
    /*End of User Interface*/



}
