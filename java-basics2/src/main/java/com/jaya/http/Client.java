package com.jaya.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;

public class Client {

    OkHttpClient okHttpClient;

    public Client(){
        okHttpClient = new OkHttpClient();
    }

    public String getUrl(String s){
        Request req = new Request.Builder()
                .url(s)
                .build();

        try{
            return okHttpClient.newCall(req).execute().body().string();
        }catch (IOException io){
            System.out.println("Error: "+io.getMessage());
            return  null;
        }

    }
}
