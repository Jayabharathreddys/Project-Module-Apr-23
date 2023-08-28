package com.jaya.rest;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface JSONPlaceHolderAPI2 {

    @GET("/photos")
    Call<List<Photo2>> getPhotos();
}
