package com.jaya.rest;

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
