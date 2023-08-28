package com.jaya.rest;

import java.io.IOException;
import java.util.List;

public class TestDownload {
    public static void main(String[] args) throws IOException {

        //var restClient = new RestClient();
        //List<Photo> photos = restClient.getApi().getPhotos().execute().body();
        //var apiResponse = restClient.getApi().getPhotos().execute();
        //apiResponse.body().forEach(photo -> System.out.println("AlbumID->"+photo.getAlbumId()+" #Titles->"+photo.getTitle()));
      //  var photoDownloader = new PhotoDownloader();
      //  photoDownloader.downloadPhotos(photos);

        var restClt = new RestClient2();
        List<Photo2> ph = restClt.getApi().getPhotos().execute().body();
        //var apiResp = restClt.getApi().getPhotos().execute();
        ph.forEach(p-> System.out.println(p.toString()));

        var photoDownloader = new PhotoDownloader2();
        photoDownloader.downloadPhoto(ph);
    }


}
