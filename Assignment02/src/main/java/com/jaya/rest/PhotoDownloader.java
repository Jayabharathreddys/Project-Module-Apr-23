package com.jaya.rest;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class PhotoDownloader {
    private final String SAVE_DIR = "/photos/";

    public void downloadPhotos(List<Photo> photos) throws IOException {
        System.out.println("Entered into downloadPhotos...");

        int limit = 10;
       // Set<Integer> albumIds = new HashSet<>();
        Map<Integer, Integer> albumCounts = new HashMap<>();

       /* for (Photo photo : photos) {
            albumIds.add(photo.getAlbumId());
            albumCounts.put(photo.getAlbumId(), albumCounts.getOrDefault(photo.getAlbumId(), 0) + 1);
        }*/

        for (Photo photo : photos) {
            String url = photo.getUrl();
            int albumId = photo.getAlbumId();
            int photoId = photo.getId();
            String fileName = "photo_" + photoId + ".jpg";
            String savePath = SAVE_DIR + "album_" + albumId + "/" + fileName;

            System.out.println("AlbumID->"+photo.getAlbumId()
                    +" #albumCounts.get(albumId)->"+albumCounts.get(albumId));

            if (albumCounts.getOrDefault(photo.getAlbumId(), 0) <= limit) {
                startDownload(url,albumId,savePath);
               // albumCounts.put(albumId, albumCounts.get(albumId) + 1);
                albumCounts.put(photo.getAlbumId(), albumCounts.getOrDefault(photo.getAlbumId(), 0) + 1);
            }

           /* if (albumCounts.get(albumId) > limit) {
                break;
            }*/
        }
    }

    private void startDownload(String url, int albumId, String savePath) throws IOException {
        OkHttpClient client = new OkHttpClient();
        var request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            InputStream inputStream = response.body().byteStream();
            File saveDir = new File(SAVE_DIR + "album_" + albumId);

            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            FileOutputStream outputStream = new FileOutputStream(savePath);
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();
        }
    }
}
