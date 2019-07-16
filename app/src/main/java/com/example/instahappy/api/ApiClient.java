package com.example.instahappy.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    @SuppressWarnings("SpellCheckingInspection")
    public static Retrofit getApiClient(){

        @SuppressWarnings("SpellCheckingInspection") OkHttpClient okhttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build();


        return new Retrofit.Builder()
                .baseUrl(IUnsplashService.URL_UNSPLASH_BASE_URL)
                .client(okhttpClientBuilder)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }



}
