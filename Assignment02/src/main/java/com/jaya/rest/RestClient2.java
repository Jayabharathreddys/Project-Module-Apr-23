package com.jaya.rest;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient2 {

    JSONPlaceHolderAPI2 api;

    public RestClient2() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

       api = retrofit.create(JSONPlaceHolderAPI2.class);
       api.getPhotos();
    }

    public JSONPlaceHolderAPI2 getApi(){
        return api;
 }
}
