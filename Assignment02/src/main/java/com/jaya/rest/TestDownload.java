package com.jaya.rest;

import java.io.IOException;
import java.util.List;

public class TestDownload {
    public static void main(String[] args) throws IOException {

        var restClient = new RestClient();
        List<Photo> photos = restClient.getApi().getPhotos().execute().body();
        var apiResponse = restClient.getApi().getPhotos().execute();
        apiResponse.body().forEach(photo -> System.out.println("AlbumID->"+photo.getAlbumId()+" #Titles->"+photo.getTitle()));
        var photoDownloader = new PhotoDownloader();
        photoDownloader.downloadPhotos(photos);
    }


}
