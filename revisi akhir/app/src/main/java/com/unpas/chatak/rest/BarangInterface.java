package com.unpas.chatak.rest;

import com.unpas.chatak.model.ApiModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface BarangInterface {

    @GET("/services?chatak-api-key=admin123")
    Call<List<ApiModel>> getBarang();


}