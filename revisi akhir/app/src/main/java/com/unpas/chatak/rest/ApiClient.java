package com.unpas.chatak.rest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.unpas.chatak.main_page.HomeFragment;
import com.unpas.chatak.main_page.WishlistFragment;
import com.unpas.chatak.model.ApiModel;
import com.unpas.chatak.model.WishlistModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient extends ViewModel {

    public static final String BASE_URL = "http://chatak-api.000webhostapp.com";
//    public static final String BASE_URL = "http://api.chatak.xyz";
//    public static final String BASE_URL = "http://192.168.1.4/chatak-api";

    public static final String IMG_URL="http://www.chatak.xyz/resources/assets/img";
    private static Retrofit retrofit;

    public static Retrofit getClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30,TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

//async cache data
public static MutableLiveData<List<ApiModel>> DOKUMEN = null;
    public LiveData<List<ApiModel>> getDokumenCache() {
        if (DOKUMEN == null) {
            DOKUMEN = new MutableLiveData<>();
            loadDokumen();
        }
        return DOKUMEN;
    }

    //get data JSON data dari fragment
    private void loadDokumen() {
        HomeFragment dokumen = new HomeFragment();
        dokumen.loadDataApi();
    }

  }
