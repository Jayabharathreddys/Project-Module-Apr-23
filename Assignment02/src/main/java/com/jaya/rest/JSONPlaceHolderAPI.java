package com.jaya.rest;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface JSONPlaceHolderAPI {

    @GET("/photos")
    Call<List<Photo>> getPhotos();
}
