package com.jaya.rest;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    JSONPlaceholderAPI api;

    public RestClient(JSONPlaceholderAPI api) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        api = retrofit.create(JSONPlaceholderAPI.class);


    }

    public JSONPlaceholderAPI getApi(){
        return api;
    }
}
